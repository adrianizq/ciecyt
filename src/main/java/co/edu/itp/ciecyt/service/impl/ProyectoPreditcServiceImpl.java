package co.edu.itp.ciecyt.service.impl;

import co.edu.itp.ciecyt.domain.Facultad;
import co.edu.itp.ciecyt.domain.Modalidad;
import co.edu.itp.ciecyt.domain.Proyecto;
import co.edu.itp.ciecyt.repository.ProyectoRepository;
import co.edu.itp.ciecyt.service.*;
import co.edu.itp.ciecyt.service.dto.InvestigacionTipoDTO;
import co.edu.itp.ciecyt.service.dto.ProgramaDTO;
import co.edu.itp.ciecyt.service.dto.ProyectoDTO;
import co.edu.itp.ciecyt.service.mapper.ProyectoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.evaluation.Evaluation;
import weka.classifiers.trees.J48;
import weka.core.AttributeStats;
import weka.core.Debug.Random;
import weka.core.Instances;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ProyectoPreditcServiceImpl implements ProyectoPredictService {

    private final Logger log = LoggerFactory.getLogger(ProyectoPreditcServiceImpl.class);

    private final ProyectoRepository proyectoRepository;
    private final IntegranteProyectoService integranteProyectoService;
    //private final IntegranteProyectoRepository integranteProyectoRepository;
    private final RolesModalidadService rolesModalidadService;

    private final ModalidadService modalidadService;

    private final ProyectoMapper proyectoMapper;
    private DataSourceProperties dataSourceProperties;

    private InvestigacionTipoService investigacionTipoService;
    private FacultadService facultadService;

    private ProgramaService programaService;

    private boolean add;

    public ProyectoPreditcServiceImpl(
        ProyectoRepository proyectoRepository,
        ProyectoMapper proyectoMapper,
        IntegranteProyectoService integranteProyectoService,
        RolesModalidadService rolesModalidadService,
        DataSourceProperties dataSourceProperties,
        InvestigacionTipoService investigacionTipoService,
        ProgramaService programaService,
        ModalidadService modalidadService,
        FacultadService facultadService
    ) {
        this.proyectoRepository = proyectoRepository;
        this.proyectoMapper = proyectoMapper;
        this.integranteProyectoService = integranteProyectoService;
        this.rolesModalidadService = rolesModalidadService;
        this.dataSourceProperties = dataSourceProperties;
        this.investigacionTipoService = investigacionTipoService;
        this.programaService = programaService;
        this.modalidadService = modalidadService;
        this.facultadService = facultadService;
    }


    public List<String> predicePlay(Long tipo) throws Exception {
        List<String> estadisticas = new ArrayList<String>();
        List<ProgramaDTO> programaDTOs;
        String programaCad = "";
        programaDTOs = programaService.findAll();
        List<String> programas = new ArrayList<String>();
        for (ProgramaDTO pDto : programaDTOs) {
            programas.add(pDto.getPrograma());
            programaCad += "'" + pDto.getPrograma() + "',";
            System.out.println(pDto.getPrograma());
        }
        programaCad = programaCad.substring(0, programaCad.length() - 1);
        //traer todos los registros de tipo
        List<InvestigacionTipoDTO> investigacionTipoDTOs;

        investigacionTipoDTOs = investigacionTipoService.findAll();
        String tipoCad = "";
        List<String> tipos = new ArrayList<String>();
        for (InvestigacionTipoDTO iDto : investigacionTipoDTOs) {
            tipos.add(iDto.getInvestigacionTipo());
            tipoCad += "'" + iDto.getInvestigacionTipo() + "',";
            System.out.println(iDto.getInvestigacionTipo());
        }
        tipoCad = tipoCad.substring(0, tipoCad.length() - 1);
        //traer todos los registros de modalidad
        List<Modalidad> modalidads;
        String modalidadCad = "";
        modalidads = modalidadService.buscarAll();
        List<String> modalidades = new ArrayList<String>();
        for (Modalidad mo : modalidads) {
            modalidades.add(mo.getModalidad());
            modalidadCad += "'" + mo.getModalidad() + "',";
            System.out.println(mo.getModalidad());
        }
        modalidadCad = modalidadCad.substring(0, modalidadCad.length() - 1);
        //traer todos los registros de facultad
        List<Facultad> facultads;

        String facultadCad = "";
        facultads = facultadService.buscarAll();
        List<String> facultades = new ArrayList<String>();
        for (Facultad fa : facultads) {
            facultades.add(fa.getFacultad());
            facultadCad += "'" + fa.getFacultad() + "',";
            System.out.println(fa.getFacultad());
        }
        facultadCad = facultadCad.substring(0, facultadCad.length() - 1);

        Proyecto dataProyecto = new Proyecto();
        //Recuperar todos los proyectos
        List<Proyecto> lDataProyecto = proyectoRepository.findAll();
        //Construir la informacion que va ir en el archivo ARFF
        String info = "";
        for (Proyecto p : lDataProyecto) {
            info += "'";
            info += p.getPrograma();
            info += "'";
            info += ",";
            info += "'";
            info += p.getProyectoModalidad().getModalidad();
            info += "'";
            info += ",";
            info += "'";
            info += p.getFacultad().getFacultad();
            info += "'";
            info += ",";
            info += "'";
            info += p.getTipo();
            info += "'";
            info += "\n";
        }
        //Declarando un archivo
        File file = null;
        FileWriter filewriter = null;
        //Construyendo la Data del archivo ARFF
        String data =
            "@relation Proyectos\n" +
            "@attribute programa {" +
            programaCad +
            "}" +
            "\n" +
            "@attribute modalidad {" +
            modalidadCad +
            "}" +
            "\n" +
            "@attribute facultad {" +
            facultadCad +
            "}" +
            "\n" +
            "@attribute tipo {" +
            tipoCad +
            "}" +
            "\n" +
            "@data" +
            "\n" +
            info;
        try {
            file = new File("wkfile.arff");
            filewriter = new FileWriter(file);
            //Escribiendo en el archivo
            filewriter.write(data);
            //Cerrando el stream
            filewriter.close();
            System.out.println("File writing done.");
        } catch (Exception e) { //Manejo de Exception
            e.printStackTrace();
        } finally {
            try {
                if (filewriter != null) {
                    filewriter.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //Abriendo el archivo creado
        String proyectoNominalDataset = "wkfile.arff";
        // Creando bufferedreader para leer el dataset
        BufferedReader bufferedReader = new BufferedReader(new FileReader(proyectoNominalDataset));
        // Crargando las instancias del archivo ARFF
        Instances datasetInstances = new Instances(bufferedReader);
        //Si el tipo del parámetro es 0 se calculan las predicciones con el metodo naive bayes
        if (tipo == 0) {
            estadisticas = addStatistisNaiveBayes(datasetInstances, tipos.size(), programas);
        }//Si el tipo del parámetro es 1 se calculan las predicciones con el metodo J48
        else {
            estadisticas = addStatistisJ48(datasetInstances, tipos.size(), programas);
        }
        //devuelve las estadísticas calculadas
        return estadisticas;
    }

    private List<String> addStatistisNaiveBayes(Instances instances, int size, List<String> programas) throws Exception {
        List<String> estadisticas = new ArrayList<String>(size);
        String resultado = new String();
        // Create naivebayes classifier //
        //https://pocketstudyblog.wordpress.com/2018/10/30/simple-naive-bayes-classification-using-weka-api-in-java/
        //Load the instances
        Instances train_data = instances;
        //Set the class index to the last attribute – i.e. Class value
        train_data.setClassIndex(train_data.numAttributes() - 1);
        //Load the Naive Bayes classifier
        NaiveBayes nb = new NaiveBayes();
        //Build the classifier with the training data
        nb.buildClassifier(train_data);
        //Perform 10 fold Cross-validation of the model
        Evaluation eval = new Evaluation(train_data);
        eval.crossValidateModel(nb, train_data, 10, new Random(1));

        for (int i = 0; i < size; i++) {
            resultado += "F1 Measure = " + eval.fMeasure(i) + "\n";
            resultado += "Precision=" + eval.precision(i) + "\n";
            resultado += "Recall=" + eval.recall(i) + "\n";
            resultado += "TNR=" + eval.trueNegativeRate(i) + "\n";
            resultado += "TPR=" + eval.truePositiveRate(i) + "\n";
            resultado += "FNR=" + eval.falseNegativeRate(i) + "\n";
            resultado += "FPR=" + eval.falsePositiveRate(i) + "\n";
            resultado += "Matrix=" + eval.toMatrixString() + "\n";
            estadisticas.add(resultado);
            resultado = new String();
        }
        //https://stackoverflow.com/questions/41436906/weka-how-to-find-distince-values-of-an-attribute
        resultado += "Método Naive Bayes\n";
        for (int i = 0; i < instances.numAttributes(); i++) {
            AttributeStats as = instances.attributeStats(i);
            String cad = new String();
            cad = instances.attribute(i).toString();
            int keyOn = cad.indexOf('{');
            cad = cad.substring(keyOn + 1, cad.length() - 3);
            String[] attrs = cad.split(",");
            //resultado += as.toString();

            resultado += instances.attribute(i);
            resultado += "instancias: " + as.nominalCounts.length + "\n";
            resultado += "<table border>";
            resultado += "<tr><th>Etiqueta</th><th>Cantidad</th><th>Peso</th></tr>";
            for (int j = 0; j < as.nominalCounts.length; j++) {
                resultado += "<tr><td>";
                resultado += attrs[j];
                resultado += "</td>";
                resultado += "<td>";
                resultado += as.nominalCounts[j];
                resultado += "</td>";
                resultado += "<td>";
                resultado += as.nominalWeights[j];
                resultado += "</td></tr>";
            }
            resultado += "</table>";
        }
        resultado += eval.toSummaryString("\n\n Results \n\n", true);
        estadisticas.add(resultado);
        return estadisticas;
    }

    //https://pocketstudyblog.wordpress.com/2018/10/30/simple-naive-bayes-classification-using-weka-api-in-java-j48-example/
    private List<String> addStatistisJ48(Instances instances, int size, List<String> programas) throws Exception {
        List<String> estadisticas = new ArrayList<String>(size);
        String resultado = new String();
        Instances train_data = new Instances(instances);
        //Set the class index to the last attribute – i.e. Class value
        train_data.setClassIndex(train_data.numAttributes() - 1);
        String[] options = { "-C", "0.1", "-M", "5" };
        //Build the classifier with the specified options and training data
        J48 tree = new J48();
        tree.setOptions(options);
        tree.buildClassifier(train_data);
        //Perform 5 fold Cross-validation of the model
        Evaluation eval = new Evaluation(train_data);
        eval.crossValidateModel(tree, train_data, 7, new Random(1));
        for (int i = 0; i < size; i++) {
            resultado += "F1 Measure = " + eval.fMeasure(i) + "\n";
            resultado += "Precision=" + eval.precision(i) + "\n";
            resultado += "Recall=" + eval.recall(i) + "\n";
            resultado += "TNR=" + eval.trueNegativeRate(i) + "\n";
            resultado += "TPR=" + eval.truePositiveRate(i) + "\n";
            resultado += "FNR=" + eval.falseNegativeRate(i) + "\n";
            resultado += "FPR=" + eval.falsePositiveRate(i) + "\n";
            resultado += "Matrix=" + eval.toMatrixString() + "\n";
            estadisticas.add(resultado);
            resultado = new String();
        }
        //https://stackoverflow.com/questions/41436906/weka-how-to-find-distince-values-of-an-attribute
        resultado += "Método J48\n";
        for (int i = 0; i < instances.numAttributes(); i++) {
            AttributeStats as = instances.attributeStats(i);
            String cad = new String();
            cad = instances.attribute(i).toString();
            int keyOn = cad.indexOf('{');
            cad = cad.substring(keyOn + 1, cad.length() - 3);
            String[] attrs = cad.split(",");

            resultado += instances.attribute(i);
            resultado += "instancias: " + as.nominalCounts.length + "\n";
            resultado += "<table border>";
            resultado += "<tr><th>Etiqueta</th><th>Cantidad</th><th>Peso</th></tr>";
            for (int j = 0; j < as.nominalCounts.length; j++) {
                resultado += "<tr><td>";
                resultado += attrs[j];
                resultado += "</td>";
                resultado += "<td>";
                resultado += as.nominalCounts[j];
                resultado += "</td>";
                resultado += "<td>";
                resultado += as.nominalWeights[j];
                resultado += "</td></tr>";
            }
            resultado += "</table>";
        }
        resultado += eval.toSummaryString("\n\n Results \n\n", true);
        estadisticas.add(resultado);
        return estadisticas;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProyectoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Proyectos");
        return proyectoRepository.findAll(pageable).map(proyectoMapper::toDto);
    }
}
