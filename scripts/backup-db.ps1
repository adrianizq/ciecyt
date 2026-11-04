param(
    [string]$Database = "ciecyt_db",
    [string]$User = "postgres",
    [string]$HostName = "localhost",
    [int]$Port = 5432,
    [string]$OutputDir = "backups",
    [string]$PgBin = "C:\Program Files\PostgreSQL\17\bin"
)

$ErrorActionPreference = "Stop"

if (-not $env:PGPASSWORD) {
    throw "Defina la variable de entorno PGPASSWORD antes de ejecutar el respaldo."
}

$pgDump = Join-Path $PgBin "pg_dump.exe"
if (-not (Test-Path -LiteralPath $pgDump)) {
    throw "No se encontro pg_dump en $pgDump"
}

if (-not (Test-Path -LiteralPath $OutputDir)) {
    New-Item -ItemType Directory -Path $OutputDir | Out-Null
}

$fecha = Get-Date -Format "yyyyMMdd-HHmmss"
$archivo = Join-Path $OutputDir "$Database-$fecha.dump"

& $pgDump -h $HostName -p $Port -U $User -d $Database -Fc -f $archivo
if ($LASTEXITCODE -ne 0) {
    throw "pg_dump fallo con codigo $LASTEXITCODE"
}

Write-Host "Respaldo creado: $archivo" -ForegroundColor Green
