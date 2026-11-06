# Run in PowerShell from the project root. Requires only JDK 17+.
$ErrorActionPreference = 'Stop'
Set-Location $PSScriptRoot
New-Item -ItemType Directory -Force -Path 'out' | Out-Null
$sources = @(Get-ChildItem -Path 'src/main/java' -Filter '*.java' -Recurse | ForEach-Object { $_.FullName })
if ($sources.Count -eq 0) { throw 'No Java source files found.' }
& javac -encoding UTF-8 -d 'out' @sources
if ($LASTEXITCODE -ne 0) { exit $LASTEXITCODE }
Write-Host '--- RUN EXERCISE 6 + 7 ---'
& java -cp 'out' demo.DemoAll
if ($LASTEXITCODE -ne 0) { exit $LASTEXITCODE }
Write-Host "`n--- AUTOMATED VERIFICATION ---"
& java -cp 'out' demo.VerificationRunner
exit $LASTEXITCODE
