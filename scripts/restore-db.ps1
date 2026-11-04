param(
    [Parameter(Mandatory = $true)]
    [string]$File,
    [string]$Database = "ciecyt_db",
    [string]$User = "postgres",
    [string]$HostName = "localhost",
    [int]$Port = 5432,
    [string]$PgBin = "C:\Program Files\PostgreSQL\17\bin",
    [switch]$Confirm
)

$ErrorActionPreference = "Stop"

if (-not $Confirm) {
    throw "La restauracion es destructiva. Repita el comando con -Confirm."
}
if (-not $env:PGPASSWORD) {
    throw "Defina la variable de entorno PGPASSWORD antes de ejecutar la restauracion."
}
if (-not (Test-Path -LiteralPath $File)) {
    throw "No existe el archivo de respaldo: $File"
}

$pgRestore = Join-Path $PgBin "pg_restore.exe"
if (-not (Test-Path -LiteralPath $pgRestore)) {
    throw "No se encontro pg_restore en $pgRestore"
}

& $pgRestore -h $HostName -p $Port -U $User -d $Database --clean --if-exists --no-owner $File
if ($LASTEXITCODE -ne 0) {
    throw "pg_restore fallo con codigo $LASTEXITCODE"
}

Write-Host "Restauracion completada sobre $Database" -ForegroundColor Green
