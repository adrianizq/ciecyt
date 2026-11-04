-- Acuerdo 024 de 2025: áreas, núcleos, líneas y sublíneas de investigación institucionales.
-- Se cargan las sublíneas que corresponden a los programas académicos existentes.
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
), datos(programa_id, sublinea) AS (
    VALUES
    (27103, 'Gestión de la innovación verde'),
    (27103, 'Gestión empresarial contable, tributaria y financiera'),
    (27103, 'Desarrollo organizacional y gestión del talento humano'),
    (27103, 'Asociatividad y encadenamiento productivo'),
    (27103, 'Emprendimiento'),
    (27103, 'Negocios verdes y biocomercio sostenible'),
    (53651, 'Gestión de la innovación verde'),
    (53651, 'Gestión empresarial contable, tributaria y financiera'),
    (53651, 'Desarrollo organizacional y gestión del talento humano'),
    (53651, 'Asociatividad y encadenamiento productivo'),
    (53651, 'Emprendimiento'),
    (53651, 'Negocios verdes y biocomercio sostenible'),
    (53652, 'Gestión de la innovación verde'),
    (53652, 'Gestión empresarial contable, tributaria y financiera'),
    (53652, 'Desarrollo organizacional y gestión del talento humano'),
    (53652, 'Asociatividad y encadenamiento productivo'),
    (53652, 'Emprendimiento'),
    (53652, 'Negocios verdes y biocomercio sostenible'),
    (27102, 'Gestión de la innovación verde'),
    (27102, 'Gestión empresarial contable, tributaria y financiera'),
    (27102, 'Desarrollo organizacional y gestión del talento humano'),
    (27102, 'Asociatividad y encadenamiento productivo'),
    (27102, 'Emprendimiento'),
    (27102, 'Negocios verdes y biocomercio sostenible'),
    (27101, 'Informática Educativa'),
    (27101, 'Multimedia'),
    (27101, 'Aplicaciones para internet'),
    (27101, 'Ingeniería de Software'),
    (27101, 'Teleinformática'),
    (27101, 'Tecnologías de la información / Gestión de proyectos de TI'),
    (27101, 'Seguridad Informática'),
    (27101, 'Redes, telecomunicaciones, telemática'),
    (27101, 'Sistemas electrónicos'),
    (27101, 'Nuevas Tecnologías de información'),
    (26551, 'Informática Educativa'),
    (26551, 'Multimedia'),
    (26551, 'Aplicaciones para internet'),
    (26551, 'Ingeniería de Software'),
    (26551, 'Teleinformática'),
    (26551, 'Tecnologías de la información / Gestión de proyectos de TI'),
    (26551, 'Seguridad Informática'),
    (26551, 'Redes, telecomunicaciones, telemática'),
    (26551, 'Sistemas electrónicos'),
    (26551, 'Nuevas Tecnologías de información'),
    (50551, 'Materiales de construcción'),
    (50551, 'Mecánica de suelos y fluidos'),
    (50551, 'Análisis sísmico'),
    (50551, 'Ambientes urbanos y rurales sostenibles'),
    (50551, 'Diseño de vivienda sostenible adaptada a las condiciones climáticas y culturales de la región amazónica'),
    (50551, 'Preservación y valoración del patrimonio arquitectónico de las comunidades indígenas'),
    (50551, 'Planeación urbana y ordenamiento territorial sostenible'),
    (53801, 'Materiales de construcción'),
    (53801, 'Mecánica de suelos y fluidos'),
    (53801, 'Análisis sísmico'),
    (53801, 'Ambientes urbanos y rurales sostenibles'),
    (53801, 'Diseño de vivienda sostenible adaptada a las condiciones climáticas y culturales de la región amazónica'),
    (53801, 'Preservación y valoración del patrimonio arquitectónico de las comunidades indígenas'),
    (53801, 'Planeación urbana y ordenamiento territorial sostenible'),
    (53301, 'Ordenamiento territorial'),
    (53301, 'Biotecnología'),
    (53301, 'Producción agropecuaria, seguridad alimentaria'),
    (53301, 'Cadenas forestales maderables y no maderables'),
    (29901, 'Ciencia, ingeniería y nuevas tecnologías'),
    (29901, 'Gestión y sostenibilidad ambiental'),
    (29901, 'Calidad del hábitat y el entorno'),
    (29901, 'Valoración de servicios ecosistémicos')
)
INSERT INTO linea_investigacion (id, linea, codigo_linea, linea_padre_id, linea_investigacion_programa_id)
SELECT nextval('sequence_generator'), d.sublinea, NULL, r.id, d.programa_id
FROM datos d
JOIN raiz r ON true
JOIN programa p ON p.id = d.programa_id
WHERE NOT EXISTS (
    SELECT 1 FROM linea_investigacion l
    WHERE l.linea_padre_id = r.id
      AND l.linea_investigacion_programa_id = d.programa_id
      AND lower(l.linea) = lower(d.sublinea)
);

-- Reasignar proyectos cuya sublínea pertenece a otro programa pero tiene el mismo nombre.
UPDATE proyecto p
SET sub_linea_linea_investigacion_id = l2.id
FROM linea_investigacion l1, linea_investigacion l2
WHERE p.sub_linea_linea_investigacion_id = l1.id
  AND l2.linea_padre_id = l1.linea_padre_id
  AND l2.linea_investigacion_programa_id = p.proyecto_programa_id
  AND lower(l2.linea) = lower(l1.linea)
  AND l1.linea_investigacion_programa_id IS DISTINCT FROM p.proyecto_programa_id;
