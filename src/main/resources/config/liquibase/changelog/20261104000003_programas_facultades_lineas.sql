-- Facultad y programas de la oferta académica 2026, con sus sublíneas del Acuerdo 024.
INSERT INTO facultad (id, codigo_facultad, facultad)
SELECT 40001, '4', 'Ciencias Agropecuarias, Forestales y Veterinaria'
WHERE NOT EXISTS (
    SELECT 1 FROM facultad WHERE lower(facultad) = lower('Ciencias Agropecuarias, Forestales y Veterinaria')
);

SELECT setval('programa_id_seq', (SELECT COALESCE(MAX(id), 1) FROM programa), true);

WITH datos(programa, ciclo, facultad_id) AS (
    VALUES
    ('Medicina Veterinaria', 'Profesional', 40001),
    ('Técnico Profesional en Desarrollo de Aplicaciones Web', 'Técnico', 30001),
    ('Técnico Profesional en Producción Gráfica y Multimedia', 'Técnico', 30001),
    ('Ingeniería Ambiental', 'Profesional', 4753),
    ('Tecnología en Recursos Forestales', 'Tecnológico', 4753),
    ('Tecnología en Producción Agroindustrial', 'Tecnológico', 4753),
    ('Ingeniería Agroindustrial', 'Profesional', 4753),
    ('Tecnología en Gestión de Comercio Exterior', 'Tecnológico', 25001),
    ('Administración de Negocios Internacionales', 'Profesional', 25001),
    ('Tecnología en Gestión Gastronómica', 'Tecnológico', 25001),
    ('Profesional en Gastronomía', 'Profesional', 25001),
    ('Tecnología en Gestión Pública', 'Tecnológico', 25001),
    ('Especialización en Seguridad Informática', 'Posgrado', 30001),
    ('Especialización en Gerencia del Talento Humano', 'Posgrado', 25001),
    ('Especialización en Gestión Ambiental', 'Posgrado', 4753)
)
INSERT INTO programa (id, programa, ciclo, programa_facultad_id)
SELECT nextval('programa_id_seq'), d.programa, d.ciclo, d.facultad_id
FROM datos d
WHERE NOT EXISTS (
    SELECT 1 FROM programa p WHERE lower(p.programa) = lower(d.programa)
);

INSERT INTO linea_investigacion (id, linea, codigo_linea, linea_padre_id, linea_investigacion_programa_id)
SELECT nextval('sequence_generator'), 'Transformación del Territorio y Desarrollo Regional Sostenible', '01', NULL, NULL
WHERE NOT EXISTS (
    SELECT 1 FROM linea_investigacion
    WHERE linea_padre_id IS NULL AND lower(linea) LIKE 'transformación del territorio%'
);

WITH raiz AS (
    SELECT id FROM linea_investigacion
    WHERE linea_padre_id IS NULL AND lower(linea) LIKE 'transformación del territorio%'
    ORDER BY id LIMIT 1
), datos(programa, sublinea) AS (
    VALUES
    ('Medicina Veterinaria', 'Sanidad animal y control de enfermedades zoonóticas.'),
    ('Medicina Veterinaria', 'Producción animal sostenible en ecosistemas amazónicos.'),
    ('Medicina Veterinaria', 'Conservación de fauna silvestre y manejo de especies nativas.'),
    ('Medicina Veterinaria', 'Nuevas herramientas y tecnologías en veterinaria y zootecnia'),
    ('Medicina Veterinaria', 'Impacto de la educación en salud sobre el control de enfermedades tropicales'),
    ('Medicina Veterinaria', 'Factores psicosociales que afectan la adherencia a tratamientos en pacientes con enfermedades crónicas no transmisibles'),
    ('Técnico Profesional en Desarrollo de Aplicaciones Web', 'Informática Educativa'),
    ('Técnico Profesional en Desarrollo de Aplicaciones Web', 'Multimedia'),
    ('Técnico Profesional en Desarrollo de Aplicaciones Web', 'Aplicaciones para internet'),
    ('Técnico Profesional en Desarrollo de Aplicaciones Web', 'Ingeniería de Software'),
    ('Técnico Profesional en Desarrollo de Aplicaciones Web', 'Teleinformática'),
    ('Técnico Profesional en Desarrollo de Aplicaciones Web', 'Tecnologías de la información / Gestión de proyectos de TI'),
    ('Técnico Profesional en Desarrollo de Aplicaciones Web', 'Seguridad Informática'),
    ('Técnico Profesional en Desarrollo de Aplicaciones Web', 'Redes, telecomunicaciones, telemática'),
    ('Técnico Profesional en Desarrollo de Aplicaciones Web', 'Sistemas electrónicos'),
    ('Técnico Profesional en Desarrollo de Aplicaciones Web', 'Nuevas Tecnologías de información'),
    ('Técnico Profesional en Producción Gráfica y Multimedia', 'Periodismo digital en la difusión en la región amazónica.'),
    ('Técnico Profesional en Producción Gráfica y Multimedia', 'Análisis del discurso mediático sobre problemáticas sociales en los medios locales'),
    ('Técnico Profesional en Producción Gráfica y Multimedia', 'Diseño gráfico como herramienta para la preservación y difusión de la cultura visual indígena del Putumayo.'),
    ('Técnico Profesional en Producción Gráfica y Multimedia', 'Impacto del diseño gráfico en campañas de sensibilización.'),
    ('Técnico Profesional en Producción Gráfica y Multimedia', 'Uso del diseño gráfico digital para el fortalecimiento de la identidad visual.'),
    ('Ingeniería Ambiental', 'Ciencia, ingeniería y nuevas tecnologías'),
    ('Ingeniería Ambiental', 'Gestión y sostenibilidad ambiental'),
    ('Ingeniería Ambiental', 'Calidad del hábitat y el entorno'),
    ('Ingeniería Ambiental', 'Valoración de servicios ecosistémicos'),
    ('Tecnología en Recursos Forestales', 'Ordenamiento territorial'),
    ('Tecnología en Recursos Forestales', 'Biotecnología'),
    ('Tecnología en Recursos Forestales', 'Producción agropecuaria, seguridad alimentaria'),
    ('Tecnología en Recursos Forestales', 'Cadenas forestales maderables y no maderables'),
    ('Tecnología en Producción Agroindustrial', 'Análisis y evaluación de la seguridad alimentaria'),
    ('Tecnología en Producción Agroindustrial', 'Desarrollo e Innovación de procesos y productos provenientes del sector agropecuario y cadenas productivas'),
    ('Tecnología en Producción Agroindustrial', 'Cocina Tradicional y Patrimonio Cultural'),
    ('Ingeniería Agroindustrial', 'Análisis y evaluación de la seguridad alimentaria'),
    ('Ingeniería Agroindustrial', 'Desarrollo e Innovación de procesos y productos provenientes del sector agropecuario y cadenas productivas'),
    ('Ingeniería Agroindustrial', 'Cocina Tradicional y Patrimonio Cultural'),
    ('Tecnología en Gestión de Comercio Exterior', 'Gestión de la innovación verde'),
    ('Tecnología en Gestión de Comercio Exterior', 'Gestión empresarial contable, tributaria y financiera'),
    ('Tecnología en Gestión de Comercio Exterior', 'Desarrollo organizacional y gestión del talento humano'),
    ('Tecnología en Gestión de Comercio Exterior', 'Asociatividad y encadenamiento productivo'),
    ('Tecnología en Gestión de Comercio Exterior', 'Emprendimiento'),
    ('Tecnología en Gestión de Comercio Exterior', 'Negocios verdes y biocomercio sostenible'),
    ('Administración de Negocios Internacionales', 'Gestión de la innovación verde'),
    ('Administración de Negocios Internacionales', 'Gestión empresarial contable, tributaria y financiera'),
    ('Administración de Negocios Internacionales', 'Desarrollo organizacional y gestión del talento humano'),
    ('Administración de Negocios Internacionales', 'Asociatividad y encadenamiento productivo'),
    ('Administración de Negocios Internacionales', 'Emprendimiento'),
    ('Administración de Negocios Internacionales', 'Negocios verdes y biocomercio sostenible'),
    ('Tecnología en Gestión Gastronómica', 'Análisis y evaluación de la seguridad alimentaria'),
    ('Tecnología en Gestión Gastronómica', 'Desarrollo e Innovación de procesos y productos provenientes del sector agropecuario y cadenas productivas'),
    ('Tecnología en Gestión Gastronómica', 'Cocina Tradicional y Patrimonio Cultural'),
    ('Profesional en Gastronomía', 'Análisis y evaluación de la seguridad alimentaria'),
    ('Profesional en Gastronomía', 'Desarrollo e Innovación de procesos y productos provenientes del sector agropecuario y cadenas productivas'),
    ('Profesional en Gastronomía', 'Cocina Tradicional y Patrimonio Cultural'),
    ('Tecnología en Gestión Pública', 'Gestión de la innovación verde'),
    ('Tecnología en Gestión Pública', 'Gestión empresarial contable, tributaria y financiera'),
    ('Tecnología en Gestión Pública', 'Desarrollo organizacional y gestión del talento humano'),
    ('Tecnología en Gestión Pública', 'Asociatividad y encadenamiento productivo'),
    ('Tecnología en Gestión Pública', 'Emprendimiento'),
    ('Tecnología en Gestión Pública', 'Negocios verdes y biocomercio sostenible'),
    ('Especialización en Seguridad Informática', 'Informática Educativa'),
    ('Especialización en Seguridad Informática', 'Multimedia'),
    ('Especialización en Seguridad Informática', 'Aplicaciones para internet'),
    ('Especialización en Seguridad Informática', 'Ingeniería de Software'),
    ('Especialización en Seguridad Informática', 'Teleinformática'),
    ('Especialización en Seguridad Informática', 'Tecnologías de la información / Gestión de proyectos de TI'),
    ('Especialización en Seguridad Informática', 'Seguridad Informática'),
    ('Especialización en Seguridad Informática', 'Redes, telecomunicaciones, telemática'),
    ('Especialización en Seguridad Informática', 'Sistemas electrónicos'),
    ('Especialización en Seguridad Informática', 'Nuevas Tecnologías de información'),
    ('Especialización en Gerencia del Talento Humano', 'Gestión de la innovación verde'),
    ('Especialización en Gerencia del Talento Humano', 'Gestión empresarial contable, tributaria y financiera'),
    ('Especialización en Gerencia del Talento Humano', 'Desarrollo organizacional y gestión del talento humano'),
    ('Especialización en Gerencia del Talento Humano', 'Asociatividad y encadenamiento productivo'),
    ('Especialización en Gerencia del Talento Humano', 'Emprendimiento'),
    ('Especialización en Gerencia del Talento Humano', 'Negocios verdes y biocomercio sostenible'),
    ('Especialización en Gestión Ambiental', 'Ciencia, ingeniería y nuevas tecnologías'),
    ('Especialización en Gestión Ambiental', 'Gestión y sostenibilidad ambiental'),
    ('Especialización en Gestión Ambiental', 'Calidad del hábitat y el entorno'),
    ('Especialización en Gestión Ambiental', 'Valoración de servicios ecosistémicos')
)
INSERT INTO linea_investigacion (id, linea, codigo_linea, linea_padre_id, linea_investigacion_programa_id)
SELECT nextval('sequence_generator'), d.sublinea, NULL, r.id, p.id
FROM datos d
JOIN raiz r ON true
JOIN programa p ON lower(p.programa) = lower(d.programa)
WHERE NOT EXISTS (
    SELECT 1 FROM linea_investigacion l
    WHERE l.linea_padre_id = r.id
      AND l.linea_investigacion_programa_id = p.id
      AND lower(l.linea) = lower(d.sublinea)
);

-- Alinear los nombres existentes con la oferta publicada en el sitio institucional.
UPDATE programa SET programa = 'Tecnología en Gestión Contable' WHERE id = 53652;
UPDATE programa SET programa = 'Tecnología en Obras Civiles' WHERE id = 53801;
UPDATE programa SET programa = 'Tecnología en Desarrollo de Software' WHERE id = 26551;
UPDATE programa SET programa = 'Contaduría Pública' WHERE id = 53651;
UPDATE programa SET programa = 'Tecnología en Gestión Empresarial y de la Innovación' WHERE id = 27102;
