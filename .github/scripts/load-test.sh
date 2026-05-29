#!/usr/bin/env bash
# Boots a headless Paper server with Skript + the built SkJadePlus jar and asserts that
# SkJadePlus enables cleanly. Used by the MC Compatibility workflow and runnable locally:
#   ./.github/scripts/load-test.sh 26.1.2 2.15.2
set -euo pipefail

MC="${1:?usage: load-test.sh <minecraft-version> <skript-version>}"
SKRIPT="${2:?usage: load-test.sh <minecraft-version> <skript-version>}"
BOOT_TIMEOUT="${BOOT_TIMEOUT:-240}"

WORK="$(pwd)/run-test"
rm -rf "$WORK"
mkdir -p "$WORK/plugins"

echo "::group::Resolve Paper $MC (v3 fill API)"
BUILD_JSON="$(curl -fsSL "https://fill.papermc.io/v3/projects/paper/versions/${MC}/builds/latest")"
PAPER_URL="$(echo "$BUILD_JSON" | jq -r '.downloads."server:default".url')"
echo "Paper download: $PAPER_URL"
if [ -z "$PAPER_URL" ] || [ "$PAPER_URL" = "null" ]; then
  echo "FAIL: could not resolve a Paper build for $MC"
  exit 1
fi
echo "::endgroup::"

echo "::group::Download server + dependencies"
curl -fSL -o "$WORK/paper.jar" "$PAPER_URL"
curl -fSL -o "$WORK/plugins/Skript.jar" \
  "https://repo.skriptlang.org/releases/com/github/SkriptLang/Skript/${SKRIPT}/Skript-${SKRIPT}.jar"
cp build/libs/SkJadePlus-*.jar "$WORK/plugins/"
ls -la "$WORK/plugins"
echo "::endgroup::"

cd "$WORK"
echo "eula=true" > eula.txt
printf 'online-mode=false\nlevel-type=minecraft\\:flat\nspawn-protection=0\nmax-players=1\nlevel-name=world\n' > server.properties

echo "::group::Boot Paper $MC"
mkfifo cmd.pipe
java -Xmx2G -jar paper.jar --nogui < cmd.pipe > server.log 2>&1 &
SPID=$!
# Hold the write end open so the server's stdin stays connected.
exec 3> cmd.pipe
result=0
for _ in $(seq 1 "$BOOT_TIMEOUT"); do
  if grep -q "Done (" server.log 2>/dev/null; then result=1; break; fi
  if ! kill -0 "$SPID" 2>/dev/null; then result=2; break; fi
  sleep 1
done
sleep 2
echo "stop" >&3 || true
wait "$SPID" 2>/dev/null || true
exec 3>&- || true
echo "Boot loop result: $result (1=started, 2=process exited early, 0=timeout)"
echo "::endgroup::"

echo "===== server.log (tail) ====="
tail -n 100 server.log || true
echo "=============================="

echo "::group::Assertions"
fail=0
if ! grep -q "Done (" server.log; then
  echo "FAIL: server never finished starting"
  fail=1
fi
if ! grep -q "\[SkJadePlus\].*has been successfully enabled" server.log; then
  echo "FAIL: SkJadePlus did not report a successful enable"
  fail=1
fi
if grep -q "Error occurred while enabling SkJadePlus" server.log; then
  echo "FAIL: an error occurred while enabling SkJadePlus"
  fail=1
fi
if grep -q "Could not load 'plugins/SkJadePlus" server.log; then
  echo "FAIL: server could not load the SkJadePlus jar"
  fail=1
fi
if [ "$fail" -eq 0 ]; then
  echo "PASS: SkJadePlus enabled cleanly on Paper $MC with Skript $SKRIPT"
fi
echo "::endgroup::"
exit "$fail"
