$env:LOG_LEVEL = "debug"
$output = npx renovate --platform=local --onboarding=false | Out-String

$pattern = '(?m)^\s*"updates": \[(?:\r?\n.*){13}'
Select-String -InputObject $output -Pattern $pattern -AllMatches |
        ForEach-Object { $_.Matches.Value } |
        Out-File updates.log