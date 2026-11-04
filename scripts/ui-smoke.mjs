import { chromium } from 'playwright-core';

const base = process.env.UI_BASE_URL || 'http://localhost:9000';
const executablePath =
  process.env.CHROME_PATH || 'C:\\Program Files\\Google\\Chrome\\Application\\chrome.exe';

const fallas = [];

function ok(nombre) {
  console.log(`[OK] ${nombre}`);
}

async function probar(nombre, fn) {
  try {
    await fn();
    ok(nombre);
  } catch (e) {
    console.error(`[FALLO] ${nombre}: ${e.message}`);
    fallas.push(nombre);
  }
}

const browser = await chromium.launch({ headless: true, executablePath });
const page = await browser.newPage();
page.setDefaultTimeout(15000);

const erroresPagina = [];
page.on('pageerror', error => erroresPagina.push(error.message));

try {
  await probar('login admin', async () => {
    await page.goto(base, { waitUntil: 'networkidle' });
    await page.getByText(/iniciar sesión/i).first().click();
    await page.waitForSelector('#username');
    await page.fill('#username', 'admin');
    await page.fill('#password', 'admin');
    await page.click('button[type="submit"]');
    await page.getByText('Administración', { exact: false }).first().waitFor({ state: 'visible', timeout: 20000 });
  });

  const paginas = [
    ['/admin/configuracion-academica', 'Configuración académica'],
    ['/admin/periodos', 'Periodos académicos'],
    ['/admin/ciclos', 'Ciclos académicos'],
    ['/admin/metricas-negocio', 'Métricas de negocio'],
    ['/admin/reportes', 'Reportes administrativos'],
    ['/admin/festivos', 'Días no hábiles'],
    ['/ciecyt/asesores-externos', 'Asesores externos'],
  ];

  for (const [ruta, texto] of paginas) {
    await probar(`pagina ${ruta}`, async () => {
      await page.goto(`${base}${ruta}`, { waitUntil: 'networkidle' });
      const body = await page.locator('body').innerText();
      if (!body.includes(texto)) {
        throw new Error(`no se encontro "${texto}"`);
      }
    });
  }

  await probar('filtro de facultad en proyectos de decanura', async () => {
    await page.goto(`${base}/decanura/proyectos`, { waitUntil: 'networkidle' });
    const opciones = await page.locator('#facultad-proyectos option').count();
    if (opciones < 2) {
      throw new Error(`solo hay ${opciones} opcion(es) de facultad`);
    }
  });

  await probar('sin errores de pagina', async () => {
    if (erroresPagina.length > 0) {
      throw new Error(erroresPagina.join(' | '));
    }
  });
} finally {
  await browser.close();
}

if (fallas.length > 0) {
  console.error(`\nFallaron ${fallas.length} pruebas: ${fallas.join(', ')}`);
  process.exit(1);
}

console.log('\nUI smoke completado correctamente.');
