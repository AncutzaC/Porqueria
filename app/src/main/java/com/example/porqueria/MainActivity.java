package com.example.porqueria;

import static com.example.porqueria.Porqueria.addGender;
import static com.example.porqueria.Porqueria.addTotalMonths;
import static java.lang.String.valueOf;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private Button resetButton, calculateWhatever;
    private EditText addYears, addMonths, addWeight, addHeight, addTAS, addTAD, addPerimeter, addBirthWeight;
    private RadioButton checkFemale, checkMale, gender;
    private RadioGroup radioGroup;

    protected Porqueria app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LayoutInflater layoutInflater = LayoutInflater.from(MainActivity.this);
        View inflatedView = layoutInflater.inflate(R.layout.activity_main, null);
        setContentView(inflatedView);

        //legam obiectele Java la xml views
        calculateWhatever = findViewById(R.id.calculateWhatever);
        resetButton = findViewById(R.id.resetButton);

        addYears = findViewById(R.id.addYears);
        EditText et = (EditText) addYears;
        et.setFilters(new InputFilter[]{ new InputFilterMinMax("0", "18")});

        addMonths = findViewById(R.id.addMonths);
        EditText et1 = (EditText) addMonths;
        et1.setFilters(new InputFilter[]{ new InputFilterMinMax("0", "11")});

        addWeight = findViewById(R.id.addWeight);
        EditText et2 = (EditText) addWeight;
        et2.setFilters(new InputFilter[]{ new InputFilterMinMax("0", "150")});

        addHeight = findViewById(R.id.addHeight);
        EditText et3 = (EditText) addHeight;
        et3.setFilters(new InputFilter[]{ new InputFilterMinMax("0", "210")});

        addTAS = findViewById(R.id.addTAS);
        EditText et4 = (EditText) addTAS;
        et4.setFilters(new InputFilter[]{ new InputFilterMinMax("0", "250")});

        addTAD = findViewById(R.id.addTAD);
        EditText et5 = (EditText) addTAD;
        et5.setFilters(new InputFilter[]{ new InputFilterMinMax("0", "250")});

        addPerimeter = findViewById(R.id.addPerimeter);
        EditText et6 = (EditText) addPerimeter;
        et6.setFilters(new InputFilter[]{ new InputFilterMinMax("0", "60")});

        addBirthWeight = findViewById(R.id.addBirthWeight);
        EditText et7 = (EditText) addBirthWeight;
        et7.setFilters(new InputFilter[]{ new InputFilterMinMax("0", "6000")});

        checkFemale = findViewById(R.id.checkFemale);
        checkMale = findViewById(R.id.checkMale);
        radioGroup = findViewById(R.id.radioGroup);

        //adaugam functionalitate la butoane

        calculateWhatever.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivity2();
                            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addYears.setText("");
                addMonths.setText("");
                addHeight.setText("");
                addWeight.setText("");
                addTAS.setText("");
                addTAD.setText("");
                addPerimeter.setText("");
                addBirthWeight.setText("");
            }
        });

        app = (Porqueria) getApplication();

    }

    // metoda care deschide fereastra 2 si transmite datele din fereastra 1 in fereastra 2
    public void openActivity2() {
        //extragem sex-ul
        int getGender = radioGroup.getCheckedRadioButtonId();
        gender = findViewById(getGender);

        if (Objects.requireNonNull(addYears.getText()).toString().trim().isEmpty())
            addYears.setText("0");
        Porqueria.addYears = Integer.parseInt(addYears.getText().toString());

        if (Objects.requireNonNull(addMonths.getText()).toString().trim().isEmpty())
            addMonths.setText("0");
        Porqueria.addMonths = Integer.parseInt(addMonths.getText().toString());

        if (Objects.requireNonNull(addHeight.getText()).toString().trim().isEmpty())
            addHeight.setText("0");
        Porqueria.addHeight = Double.parseDouble(addHeight.getText().toString());

        if (Objects.requireNonNull(addWeight.getText()).toString().trim().isEmpty())
            addWeight.setText("0");
        Porqueria.addWeight = Double.parseDouble(addWeight.getText().toString());

        if (Objects.requireNonNull(addTAD.getText()).toString().trim().isEmpty())
            addTAD.setText("0");
        Porqueria.addTAD = Integer.parseInt(addTAD.getText().toString());

        if (Objects.requireNonNull(addTAS.getText()).toString().trim().isEmpty())
            addTAS.setText("0");
        Porqueria.addTAS = Integer.parseInt(addTAS.getText().toString());

        if (Objects.requireNonNull(addPerimeter.getText()).toString().trim().isEmpty())
            addPerimeter.setText("0");
        Porqueria.addPerimeter = Double.parseDouble(addPerimeter.getText().toString());

        if (Objects.requireNonNull(addBirthWeight.getText()).toString().trim().isEmpty())
            addBirthWeight.setText("0");
        Porqueria.addBirthWeight = Double.parseDouble(addBirthWeight.getText().toString());

        addGender = gender.getText().toString();
        addTotalMonths = (Porqueria.addYears * 12) + Porqueria.addMonths;
        Porqueria.zscoreWeight = getZscoreWeight();
        Porqueria.zscoreHeight = getZscoreHeight();
        Porqueria.zscoreHeightRo =getZscoreHeightRo();
        Porqueria.procentWeightToHeightRo= getProcentWeightToHeightRo();
        Porqueria.greutateaCorespunzatoareTalieiPacientului = getGreutateCorespunzatoareTalieiPacientului();
        Porqueria.zscoreIMC = getzScoreIMC();
        Porqueria.zscorePC =getZscorePC();

        Intent intent = new Intent(MainActivity.this, MainActivity2.class);

        startActivity(intent);
    }

    private double getZscoreWeight() {
        double zscoreW = 0;

        for (int i = 1; i < listGreutatePtVarsta.size() - 3; i+=5) {
            String itemvarsta = listGreutatePtVarsta.get(i);
            float itemgen = Float.parseFloat(listGreutatePtVarsta.get(i - 1));
            String addTotalMonths = String.valueOf(Porqueria.addTotalMonths);
            double addWeight = Porqueria.addWeight;
            int gen;
            if ("Feminin" == addGender) {
                gen = 2;
            } else {
                gen = 1;
            }
            if (itemvarsta.equals(addTotalMonths) && itemgen == gen) {
                double L = Double.parseDouble(listGreutatePtVarsta.get(i + 1));
                double M = Double.parseDouble(listGreutatePtVarsta.get(i + 2));
                double S = Double.parseDouble(listGreutatePtVarsta.get(i + 3));
                double division = addWeight / M;
                zscoreW = ((double) (Math.pow(division, L) - 1)) / (L * S);
            }
        }
        return zscoreW;
    }

    private double getZscoreHeight() {
        double zscoreH = 0;
        for (int i = 1; i < listTaliePtVarsta.size() - 3; i+=5) {
        int itemvarsta = Integer.parseInt(listTaliePtVarsta.get(i));
        double itemgen = Double.parseDouble(listTaliePtVarsta.get(i - 1));
        int addTotalMonths = Porqueria.addTotalMonths;
        double addHeight = Porqueria.addHeight;
        int gen;
        if ("Feminin" == addGender) {
            gen = 2;
        } else {
            gen = 1;
        }
        if (itemvarsta == addTotalMonths && itemgen == gen) {
            double L = Double.parseDouble(listTaliePtVarsta.get(i + 1));
            double M = Double.parseDouble(listTaliePtVarsta.get(i + 2));
            double S = Double.parseDouble(listTaliePtVarsta.get(i + 3));
            double division = addHeight / M;
            zscoreH = (Math.pow(division, L) - 1) / (L * S);
        }
    } return zscoreH;
    }

    public double getZscoreHeightRo() {
        double zscoreHR = 0;
        for (int i = 0; i < listHeightRo.size() - 12; i++) {
            double itemvarsta = Double.parseDouble(listHeightRo.get(i));
            double itemvarsta2 = Double.parseDouble(listHeightRo.get(i + 7));
            double itemTalieMedieB = Double.parseDouble(listHeightRo.get(i + 4));
            double itemTalieMedieB2 = Double.parseDouble(listHeightRo.get(i + 11));
            double itemTalieMedie = Double.parseDouble(listHeightRo.get(i + 1));
            double itemTalieMedie2 = Double.parseDouble(listHeightRo.get(i + 8));
            double itemDS1 = Double.parseDouble(listHeightRo.get(i + 9));
            double itemDS = Double.parseDouble(listHeightRo.get(i + 2));
            double itemDS1B = Double.parseDouble(listHeightRo.get(i + 12));
            double itemDSB = Double.parseDouble(listHeightRo.get(i + 5));

            double addTotalMonths = Porqueria.addTotalMonths;
            double addHeight = Porqueria.addHeight;

            String addGender = Porqueria.addGender;
            if (addGender.equals("Feminin")) {
                if ((addTotalMonths < 61) && (addTotalMonths == itemvarsta)) {
                    zscoreHR = (addHeight - itemTalieMedie) / itemDS;
                }
                if (itemvarsta < addTotalMonths && itemvarsta2 > addTotalMonths && addTotalMonths > 60) {
                    zscoreHR = (addHeight - ((itemTalieMedie2 - itemTalieMedie) / 6) * (itemvarsta2 - itemvarsta))
                            / (itemDS + ((itemDS1 - itemDS) / 6) * (itemvarsta2 - itemvarsta));
                }
                if (itemvarsta == addTotalMonths && addTotalMonths > 60){
                    zscoreHR = (addHeight - itemTalieMedie) / itemDS;
                }
            }
            if (addGender.equals("Masculin")) {
                if ((addTotalMonths < 61) && (addTotalMonths == itemvarsta)) {
                    zscoreHR = (addHeight - itemTalieMedieB) / itemDSB;
                }
                if (itemvarsta < addTotalMonths && itemvarsta2 > addTotalMonths && addTotalMonths > 60) {
                    zscoreHR = (addHeight - ((itemTalieMedieB2 - itemTalieMedieB) / 6) * (itemvarsta2 - itemvarsta))
                            / (itemDSB + ((itemDS1B - itemDSB) / 6) * (itemvarsta2 - itemvarsta));
                }
                if (itemvarsta == addTotalMonths && addTotalMonths > 60){
                    zscoreHR = (addHeight - itemTalieMedieB) / itemDSB;
                }
            }
        } return zscoreHR;
    }

    public double getProcentWeightToHeightRo() {
        double procentWeightToHeightRo = 0;
        String addGender = Porqueria.addGender;
        double addHeight = Porqueria.addHeight;
        double addWeight = Porqueria.addWeight;
        int idx;
        if (addGender.equals("Feminin")) {
            for (int j = 1; j < listHeightRo.size() - 12; j += 7) {
                double talieTabel = Double.parseDouble((listHeightRo.get(j)));
                double talieTabel2 = Double.parseDouble(valueOf(listHeightRo.get(j + 7)));
                double greutateTabel = Double.parseDouble(valueOf(listHeightRo.get(j + 2)));
                double greutateTabel2 = Double.parseDouble(valueOf(listHeightRo.get(j + 9)));
                if (addHeight>talieTabel && addHeight<talieTabel2){
                    double greutateaCorespTalieiAprox = (addHeight - talieTabel) / ((talieTabel2 - talieTabel) / 6) *
                            ((greutateTabel2 - greutateTabel) / 6) + greutateTabel;
                    procentWeightToHeightRo =
                            ((addWeight - greutateaCorespTalieiAprox) * 100 )/ greutateaCorespTalieiAprox;
                }
                if (addHeight==talieTabel){
                    idx = j;
                    double greutateaCorespTalieiAprox = Double.parseDouble(valueOf(listHeightRo.get(idx + 2)));
                    procentWeightToHeightRo =
                            (addWeight - greutateaCorespTalieiAprox) * 100 / greutateaCorespTalieiAprox;
                }
            }
        }
        if (addGender.equals("Masculin")) {
            for (int j = 4; j < listHeightRo.size() - 12; j += 7) {
                double talieTabel = Double.parseDouble(valueOf(listHeightRo.get(j)));
                double talieTabel2 = Double.parseDouble(valueOf(listHeightRo.get(j + 7)));
                double greutateTabel = Double.parseDouble(valueOf(listHeightRo.get(j + 2)));
                double greutateTabel2 = Double.parseDouble(valueOf(listHeightRo.get(j + 9)));
                if (addHeight > talieTabel && addHeight < talieTabel2) {
                    double greutateaCorespTalieiAprox =
                            greutateTabel+ ((addHeight - talieTabel)*((greutateTabel2 - greutateTabel)/6) / ((talieTabel2 - talieTabel) / 6));
                    procentWeightToHeightRo =
                            (addWeight - greutateaCorespTalieiAprox) * 100 / greutateaCorespTalieiAprox;
                }
                if (addHeight == talieTabel) {
                    idx = j;
                    double greutateaCorespTalieiAprox = Double.parseDouble(valueOf(listHeightRo.get(idx + 2)));
                    procentWeightToHeightRo =
                            (addWeight - greutateaCorespTalieiAprox) * 100 / greutateaCorespTalieiAprox;
                }
            }
        }
        return procentWeightToHeightRo;
    }

    public double getGreutateCorespunzatoareTalieiPacientului() {
        double procentWeightToHeightRo = 0;
        String addGender = "Feminin";
        double addHeight = Porqueria.addHeight;
        int idx = 0;
        if ("Feminin" == addGender) {
            for (int j = 1; j < listHeightRo.size() - 12; j += 7) {
                double talieTabel = Double.parseDouble((listHeightRo.get(j)));
                double talieTabel2 = Double.parseDouble(valueOf(listHeightRo.get(j + 7)));
                double greutateTabel = Double.parseDouble(valueOf(listHeightRo.get(j + 2)));
                double greutateTabel2 = Double.parseDouble(valueOf(listHeightRo.get(j + 9)));
                if (addHeight>talieTabel && addHeight<talieTabel2){
                    double greutateaCorespTalieiAprox = (addHeight - talieTabel) / ((talieTabel2 - talieTabel) / 6) *
                            ((greutateTabel2 - greutateTabel) / 6) + greutateTabel;
                    procentWeightToHeightRo = greutateaCorespTalieiAprox;
                }
                if (addHeight==talieTabel){
                    idx = j;
                    double greutateaCorespTalieiAprox = Double.parseDouble(valueOf(listHeightRo.get(idx + 2)));
                    procentWeightToHeightRo = greutateaCorespTalieiAprox;
                }

            }
        }
        if ("Masculin" == addGender) {
            for (int j = 4; j < listHeightRo.size() - 12; j += 7) {
                double talieTabel = Double.parseDouble((listHeightRo.get(j)));
                double talieTabel2 = Double.parseDouble(valueOf(listHeightRo.get(j + 7)));
                double greutateTabel = Double.parseDouble(valueOf(listHeightRo.get(j + 2)));
                double greutateTabel2 = Double.parseDouble(valueOf(listHeightRo.get(j + 9)));
                if (addHeight>talieTabel && addHeight<talieTabel2){
                    double greutateaCorespTalieiAprox = (addHeight - talieTabel) / ((talieTabel2 - talieTabel) / 6) *
                            ((greutateTabel2 - greutateTabel) / 6) + greutateTabel;
                    procentWeightToHeightRo = greutateaCorespTalieiAprox;
                }
                if (addHeight == talieTabel) {
                    idx = j;
                    double greutateaCorespTalieiAprox = Double.parseDouble(valueOf(listHeightRo.get(idx + 2)));
                    procentWeightToHeightRo = greutateaCorespTalieiAprox;
                }
            }
        }

        return procentWeightToHeightRo;
    }

    public double getzScoreIMC() {
        double zscoreP = 0;
        for (int i = 1; i < listPercentileIMC.size() - 3; i+=5) {
            int itemvarsta = Integer.parseInt(listPercentileIMC.get(i));
            double itemgen = Double.parseDouble(listPercentileIMC.get(i - 1));
            int addTotalMonths = Porqueria.addTotalMonths;
            double imc = (Porqueria.addWeight * 10000) / (Porqueria.addHeight * Porqueria.addHeight);
            int gen;
            if ("Feminin" == addGender) {
                gen = 2;
            } else {
                gen = 1;
            }
            if (itemvarsta==addTotalMonths && itemgen == gen) {
                double L = Double.parseDouble(listPercentileIMC.get(i + 1));
                double M = Double.parseDouble(listPercentileIMC.get(i + 2));
                double S = Double.parseDouble(listPercentileIMC.get(i + 3));
                double division = imc / M;
                zscoreP = (Math.pow(division, L) - 1) / (L * S);
            }
        } return zscoreP;
    }

    public double getZscorePC() {
        double zscoreH=0;
        double addPC = Porqueria.addPerimeter;

        if (addTotalMonths < 37){
            for (int i = 1; i < listPercentilePC.size() - 3; i+=5) {
                int itemvarsta = Integer.parseInt(listPercentilePC.get(i));
                int itemgen = Integer.parseInt(listPercentilePC.get(i - 1));
                int gen;
                if ("Feminin" == addGender) {
                    gen = 2;
                } else {
                    gen = 1;
                }
                if ( itemvarsta==addTotalMonths && itemgen == gen) {
                    double L = Double.parseDouble(listPercentilePC.get(i + 1));
                    double M = Double.parseDouble(listPercentilePC.get(i + 2));
                    double S = Double.parseDouble(listPercentilePC.get(i + 3));
                    zscoreH = (Math.pow((addPC / M), L) - 1)/ (L * S);
                }
            }
        }
        if (addTotalMonths >36){
            double M = 2.350847202;
            double L = 48.63342328;
            double S = 0.031948378;
            zscoreH = (Math.pow((addPC / M), L) - 1) / (L * S);
        }
        if (addPC==0){zscoreH=0;}
        return zscoreH;

    }

    List<String> listGreutatePtVarsta = Arrays.asList(//"1", "0", "1.815151075", "3.530203168", "0.152385273",

            "1", "1", "1.815151075", "3.530203168", "0.152385273",
            "1", "2", "0.695973505", "5.672888765", "0.129677511",
            "1", "3", "0.41981509", "6.391391982", "0.124717085",
            "1", "4", "0.219866801", "7.041836432", "0.121040119",
            "1", "5", "0.077505598", "7.630425182", "0.1182712",
            "1", "6", "-0.02190761", "8.162951035", "0.116153695",
            "1", "7", "-0.0894409", "8.644832479", "0.114510349",
            "1", "8", "-0.1334091", "9.081119817", "0.113217163",
            "1", "9", "-0.1600954", "9.476500305", "0.11218624",
            "1", "10", "-0.17429685", "9.835307701", "0.11135453",
            "1", "11", "-0.1797189", "10.16153567", "0.110676413",
            "1", "12", "-0.179254", "10.45885399", "0.110118635",
            "1", "13", "-0.17518447", "10.7306256", "0.109656941",
            "1", "14", "-0.16932268", "10.97992482", "0.109273653",
            "1", "15", "-0.1631139", "11.20955529", "0.10895596",
            "1", "16", "-0.15770999", "11.4220677", "0.108694678",
            "1", "17", "-0.15402279", "11.61977698", "0.108483324",
            "1", "18", "-0.15276214", "11.80477902", "0.108317416",
            "1", "19", "-0.15446658", "11.9789663", "0.108193944",
            "1", "20", "-0.15952202", "12.14404334", "0.108110954",
            "1", "21", "-0.16817926", "12.30154103", "0.108067236",
            "1", "22", "-0.1805668", "12.45283028", "0.108062078",
            "1", "23", "-0.19670196", "12.59913494", "0.108095077",
            "1", "24", "-0.216501213", "12.74154396", "0.108166006",
            "1", "25", "-0.239790488", "12.88102276", "0.108274706",
            "1", "26", "-0.266315853", "13.01842382", "0.108421025",
            "1", "27", "-0.295754969", "13.1544966", "0.10860477",
            "1", "28", "-0.327729368", "13.28989667", "0.108825681",
            "1", "29", "-0.361817468", "13.42519408", "0.109083424",
            "1", "30", "-0.397568087", "13.56088113", "0.109377581",
            "1", "31", "-0.434520252", "13.69737858", "0.109707646",
            "1", "32", "-0.472188756", "13.83504622", "0.110073084",
            "1", "33", "-0.510116627", "13.97418299", "0.110473254",
            "1", "34", "-0.547885579", "14.1150324", "0.1109074",
            "1", "35", "-0.58507011", "14.25779618", "0.111374787",
            "1", "36", "-0.621319726", "14.40262749", "0.111874514",
            "1", "37", "-0.656295986", "14.54964614", "0.112405687",
            "1", "38", "-0.689735029", "14.69893326", "0.112967254",
            "1", "39", "-0.721410388", "14.85054151", "0.11355811",
            "1", "40", "-0.751175223", "15.00449143", "0.114176956",
            "1", "41", "-0.778904279", "15.16078454", "0.114822482",
            "1", "42", "-0.804515498", "15.31940246", "0.115493292",
            "1", "43", "-0.828003255", "15.48030313", "0.116187777",
            "1", "44", "-0.849380372", "15.64343309", "0.116904306",
            "1", "45", "-0.86869965", "15.80872535", "0.117641148",
            "1", "46", "-0.886033992", "15.97610456", "0.118396541",
            "1", "47", "-0.901507878", "16.14548194", "0.119168555",
            "1", "48", "-0.915241589", "16.31676727", "0.11995532",
            "1", "49", "-0.927377772", "16.4898646", "0.120754916",
            "1", "50", "-0.938069819", "16.66467529", "0.121565421",
            "1", "51", "-0.94747794", "16.84109948", "0.122384927",
            "1", "52", "-0.955765694", "17.01903746", "0.123211562",
            "1", "53", "-0.963096972", "17.1983908", "0.124043503",
            "1", "54", "-0.969633434", "17.37906341", "0.124878992",
            "1", "55", "-0.975532355", "17.56096245", "0.125716348",
            "1", "56", "-0.980937915", "17.74400082", "0.126554022",
            "1", "57", "-0.986006518", "17.92809121", "0.127390453",
            "1", "58", "-0.99086694", "18.11315625", "0.128224294",
            "1", "59", "-0.995644402", "18.29912286", "0.129054277",
            "1", "60", "-1.000453886", "18.48592413", "0.129879257",
            "1", "61", "-1.005399668", "18.67349965", "0.130698212",
            "1", "62", "-1.010575003", "18.86179576", "0.131510245",
            "1", "63", "-1.016061941", "19.05076579", "0.132314586",
            "1", "64", "-1.021931241", "19.24037019", "0.133110593",
            "1", "65", "-1.028242376", "19.43057662", "0.133897752",
            "1", "66", "-1.035043608", "19.62136007", "0.134675673",
            "1", "67", "-1.042372125", "19.8127028", "0.13544409",
            "1", "68", "-1.050254232", "20.0045944", "0.13620286",
            "1", "69", "-1.058705595", "20.19703171", "0.136951959",
            "1", "70", "-1.067731529", "20.39001872", "0.137691478",
            "1", "71", "-1.077321193", "20.58356862", "0.138421673",
            "1", "72", "-1.087471249", "20.77769565", "0.139142773",
            "1", "73", "-1.098152984", "20.97242631", "0.139855242",
            "1", "74", "-1.10933408", "21.16779192", "0.140559605",
            "1", "75", "-1.120974043", "21.36383013", "0.141256489",
            "1", "76", "-1.133024799", "21.56058467", "0.141946613",
            "1", "77", "-1.145431351", "21.75810506", "0.142630785",
            "1", "78", "-1.158132499", "21.95644627", "0.143309898",
            "1", "79", "-1.171061612", "22.15566842", "0.143984924",
            "1", "80", "-1.184141975", "22.35583862", "0.144656953",
            "1", "81", "-1.197307185", "22.55702268", "0.145327009",
            "1", "82", "-1.210475099", "22.75929558", "0.145996289",
            "1", "83", "-1.223565263", "22.9627344", "0.146666",
            "1", "84", "-1.236497304", "23.16741888", "0.147337375",
            "1", "85", "-1.249186293", "23.37343341", "0.148011715",
            "1", "86", "-1.261555446", "23.58086145", "0.148690256",
            "1", "87", "-1.273523619", "23.78979096", "0.149374297",
            "1", "88", "-1.285013783", "24.00031064", "0.150065107",
            "1", "89", "-1.295952066", "24.21251028", "0.150763933",
            "1", "90", "-1.306268473", "24.42648043", "0.151471982",
            "1", "91", "-1.31589753", "24.642312", "0.152190413",
            "1", "92", "-1.324778843", "24.86009596", "0.152920322",
            "1", "93", "-1.332857581", "25.07992303", "0.153662731",
            "1", "94", "-1.340080195", "25.30188584", "0.154418635",
            "1", "95", "-1.346412105", "25.52606977", "0.155188768",
            "1", "96", "-1.351813296", "25.75256528", "0.155973912",
            "1", "97", "-1.356253969", "25.9814599", "0.156774684",
            "1", "98", "-1.359710858", "26.2128399", "0.157591579",
            "1", "99", "-1.362167159", "26.44679027", "0.158424964",
            "1", "100", "-1.363612378", "26.68339457", "0.159275071",
            "1", "101", "-1.364042106", "26.92273494", "0.160141995",
            "1", "102", "-1.363457829", "27.16489199", "0.161025689",
            "1", "103", "-1.361865669", "27.40994539", "0.161925976",
            "1", "104", "-1.35928261", "27.65796978", "0.162842452",
            "1", "105", "-1.355720571", "27.90904433", "0.163774719",
            "1", "106", "-1.351202536", "28.16324264", "0.164722138",
            "1", "107", "-1.345754408", "28.42063744", "0.165683945",
            "1", "108", "-1.339405453", "28.68130005", "0.166659247",
            "1", "109", "-1.332188093", "28.94530029", "0.167647017",
            "1", "110", "-1.324137479", "29.21270645", "0.168646104",
            "1", "111", "-1.315291073", "29.48358527", "0.169655235",
            "1", "112", "-1.30568824", "29.75800198", "0.170673022",
            "1", "113", "-1.295369867", "30.03602021", "0.17169797",
            "1", "114", "-1.284374967", "30.31770417", "0.17272854",
            "1", "115", "-1.272750864", "30.60311107", "0.173762961",
            "1", "116", "-1.260539193", "30.89230072", "0.174799493",
            "1", "117", "-1.247783611", "31.18532984", "0.175836284",
            "1", "118", "-1.234527763", "31.48225315", "0.176871417",
            "1", "119", "-1.220815047", "31.78312329", "0.177902912",
            "1", "120", "-1.206688407", "32.08799062", "0.17892874",
            "1", "121", "-1.19219015", "32.39690313", "0.17994683",
            "1", "122", "-1.177361786", "32.7099062", "0.180955078",
            "1", "123", "-1.162243894", "33.02704244", "0.181951361",
            "1", "124", "-1.146876007", "33.34835148", "0.182933537",
            "1", "125", "-1.131296524", "33.67386973", "0.183899465",
            "1", "126", "-1.115542634", "34.00363017", "0.184847006",
            "1", "127", "-1.099650267", "34.33766207", "0.185774041",
            "1", "128", "-1.083654055", "34.67599076", "0.18667847",
            "1", "129", "-1.067587314", "35.01863732", "0.187558229",
            "1", "130", "-1.051482972", "35.36561737", "0.18841128",
            "1", "131", "-1.035367321", "35.71694723", "0.189235738",
            "1", "132", "-1.019277299", "36.07262569", "0.190029545",
            "1", "133", "-1.003235326", "36.43265996", "0.190790973",
            "1", "134", "-0.987269866", "36.79704392", "0.191518224",
            "1", "135", "-0.971406609", "37.1657671", "0.192209619",
            "1", "136", "-0.955670107", "37.53881268", "0.192863569",
            "1", "137", "-0.940083834", "37.91615721", "0.193478582",
            "1", "138", "-0.924670244", "38.2977703", "0.194053274",
            "1", "139", "-0.909450843", "38.6836143", "0.194586368",
            "1", "140", "-0.894446258", "39.07364401", "0.195076705",
            "1", "141", "-0.879676305", "39.46780643", "0.195523246",
            "1", "142", "-0.865160071", "39.86604044", "0.195925079",
            "1", "143", "-0.850915987", "40.26827652", "0.196281418",
            "1", "144", "-0.836961905", "40.67443658", "0.196591612",
            "1", "145", "-0.823315176", "41.08443363", "0.19685514",
            "1", "146", "-0.809992726", "41.49817164", "0.19707162",
            "1", "147", "-0.797011132", "41.91554528", "0.197240806",
            "1", "148", "-0.784386693", "42.33643978", "0.197362591",
            "1", "149", "-0.772135506", "42.76073078", "0.197437004",
            "1", "150", "-0.760273528", "43.18828419", "0.19746421",
            "1", "151", "-0.748815968", "43.61895703", "0.197444522",
            "1", "152", "-0.737780398", "44.0525931", "0.197378345",
            "1", "153", "-0.727181568", "44.48903027", "0.197266263",
            "1", "154", "-0.717035494", "44.92809483", "0.197108968",
            "1", "155", "-0.707358338", "45.36960315", "0.196907274",
            "1", "156", "-0.698166437", "45.81336172", "0.196662115",
            "1", "157", "-0.689476327", "46.25916729", "0.196374538",
            "1", "158", "-0.68130475", "46.70680701", "0.196045701",
            "1", "159", "-0.673668658", "47.15605863", "0.195676862",
            "1", "160", "-0.666585194", "47.60669074", "0.19526938",
            "1", "161", "-0.660069969", "48.05846572", "0.19482473",
            "1", "162", "-0.654142602", "48.51113138", "0.19434441",
            "1", "163", "-0.648819666", "48.96443224", "0.193830046",
            "1", "164", "-0.644118611", "49.41810374", "0.193283319",
            "1", "165", "-0.640056805", "49.87187409", "0.192705974",
            "1", "166", "-0.636651424", "50.32546478", "0.192099812",
            "1", "167", "-0.633919328", "50.77859121", "0.191466681",
            "1", "168", "-0.631876912", "51.23096332", "0.190808471",
            "1", "169", "-0.63053994", "51.68228625", "0.190127105",
            "1", "170", "-0.629923353", "52.13226113", "0.18942453",
            "1", "171", "-0.630041066", "52.58058583", "0.188702714",
            "1", "172", "-0.630905733", "53.02695588", "0.187963636",
            "1", "173", "-0.632528509", "53.47106525", "0.187209281",
            "1", "174", "-0.634918779", "53.91260737", "0.18644163",
            "1", "175", "-0.638083884", "54.35127608", "0.185662657",
            "1", "176", "-0.642028835", "54.78676659", "0.184874323",
            "1", "177", "-0.646756013", "55.21877657", "0.184078567",
            "1", "178", "-0.652262297", "55.64701131", "0.183277339",
            "1", "179", "-0.658551638", "56.07116407", "0.182472427",
            "1", "180", "-0.665609025", "56.49095862", "0.181665781",
            "1", "181", "-0.673425951", "56.90610886", "0.18085918",
            "1", "182", "-0.681987284", "57.31634059", "0.180054395",
            "1", "183", "-0.691273614", "57.72138846", "0.179253153",
            "1", "184", "-0.701261055", "58.12099696", "0.178457127",
            "1", "185", "-0.711921092", "58.51492143", "0.177667942",
            "1", "186", "-0.723218488", "58.90293208", "0.176887192",
            "1", "187", "-0.735121189", "59.28479948", "0.176116307",
            "1", "188", "-0.747580416", "59.66032626", "0.175356814",
            "1", "189", "-0.760550666", "60.02931704", "0.174610071",
            "1", "190", "-0.773984558", "60.39158721", "0.173877336",
            "1", "191", "-0.787817728", "60.74698785", "0.173159953",
            "1", "192", "-0.801993069", "61.09536847", "0.172459052",
            "1", "193", "-0.816446409", "61.43660077", "0.171775726",
            "1", "194", "-0.831110299", "61.77057372", "0.171110986",
            "1", "195", "-0.845914498", "62.09719399", "0.170465756",
            "1", "196", "-0.860786514", "62.41638628", "0.169840869",
            "1", "197", "-0.875652181", "62.72809362", "0.169237063",
            "1", "198", "-0.890436283", "63.03227756", "0.168654971",
            "1", "199", "-0.905063185", "63.32891841", "0.168095124",
            "1", "200", "-0.91945749", "63.61801537", "0.16755794",
            "1", "201", "-0.933544683", "63.89958662", "0.167043722",
            "1", "202", "-0.947251765", "64.17366943", "0.166552654",
            "1", "203", "-0.960507855", "64.44032016", "0.166084798",
            "1", "204", "-0.973244762", "64.69961427", "0.16564009",
            "1", "205", "-0.985397502", "64.95164625", "0.165218341",
            "1", "206", "-0.996904762", "65.1965295", "0.164819236",
            "1", "207", "-1.007705555", "65.43440186", "0.16444238",
            "1", "208", "-1.017756047", "65.66540015", "0.164087103",
            "1", "209", "-1.027002713", "65.88970117", "0.163752791",
            "1", "210", "-1.035402243", "66.10749114", "0.163438661",
            "1", "211", "-1.042916356", "66.31897311", "0.163143825",
            "1", "212", "-1.049511871", "66.52436618", "0.162867311",
            "1", "213", "-1.055160732", "66.72390443", "0.162608072",
            "1", "214", "-1.059840019", "66.91783563", "0.162365006",
            "1", "215", "-1.063531973", "67.10641956", "0.162136973",
            "1", "216", "-1.066224038", "67.28992603", "0.161922819",
            "1", "217", "-1.067908908", "67.46863255", "0.161721398",
            "1", "218", "-1.068589885", "67.64281378", "0.16153153",
            "1", "219", "-1.068261146", "67.8127675", "0.161352313",
            "1", "220", "-1.066933756", "67.97877331", "0.161182785",
            "1", "221", "-1.064620976", "68.14111022", "0.161022184",
            "1", "222", "-1.061341755", "68.30004741", "0.160869943",
            "1", "223", "-1.057116957", "68.4558454", "0.160725793",
            "1", "224", "-1.051988979", "68.60872174", "0.160589574",
            "1", "225", "-1.04599033", "68.75889263", "0.1604617",
            "1", "226", "-1.039168248", "68.90653028", "0.160342924",
            "1", "227", "-1.031579574", "69.05176427", "0.160234478",
            "1", "228", "-1.023291946", "69.19467288", "0.160138158",
            "1", "229", "-1.014385118", "69.33527376", "0.160056393",
            "1", "230", "-1.004952366", "69.47351373", "0.159992344",
            "1", "231", "-0.995101924", "69.60925782", "0.159949989",
            "1", "232", "-0.984958307", "69.74227758", "0.159934231",
            "1", "233", "-0.974663325", "69.87223885", "0.159951004",
            "1", "234", "-0.964376555", "69.99868896", "0.160007394",
            "1", "235", "-0.954274945", "70.12104381", "0.160111769",
            "1", "236", "-0.944551187", "70.23857482", "0.160273918",
            "1", "237", "-0.935410427", "70.35039626", "0.160505203",
            "1", "238", "-0.927059784", "70.45546105", "0.160818788",
            "1", "239", "-0.919718461", "70.55252127", "0.161229617",
            "1", "240", "-0.91648762", "70.59761453", "0.161476792",
            "2", "0", "1.357944315", "3.79752846", "0.138075916",
            "2", "1", "1.105537708", "4.544776513", "0.131733888",
            "2", "2", "0.902596648", "5.230584214", "0.126892697",
            "2", "3", "0.734121414", "5.859960798", "0.123025182",
            "2", "4", "0.590235275", "6.437587751", "0.119840911",
            "2", "5", "0.464391566", "6.967850457", "0.117166868",
            "2", "6", "0.352164071", "7.454854109", "0.11489384",
            "2", "7", "0.250497889", "7.902436186", "0.112949644",
            "2", "8", "0.15724751", "8.314178377", "0.11128469",
            "2", "9", "0.070885725", "8.693418423", "0.109863709",
            "2", "10", "-0.00968493", "9.043261854", "0.10866078",
            "2", "11", "-0.085258", "9.366593571", "0.10765621",
            "2", "12", "-0.15640945", "9.666089185", "0.106834517",
            "2", "13", "-0.22355869", "9.944226063", "0.106183085",
            "2", "14", "-0.28701346", "10.20329397", "0.105691242",
            "2", "15", "-0.34699919", "10.4454058", "0.105349631",
            "2", "16", "-0.40368918", "10.67250698", "0.105149754",
            "2", "17", "-0.45721877", "10.88638558", "0.105083666",
            "2", "18", "-0.50770077", "11.08868151", "0.105143752",
            "2", "19", "-0.55523599", "11.28089537", "0.105322575",
            "2", "20", "-0.59992113", "11.46439708", "0.10561278",
            "2", "21", "-0.64185418", "11.64043402", "0.106007025",
            "2", "22", "-0.6811381", "11.81013895", "0.106497957",
            "2", "23", "-0.71788283", "11.97453748", "0.107078197",
            "2", "24", "-0.73533951", "12.05503983", "0.107399495",
            "2", "24.5", "-0.75220657", "12.13455523", "0.107740345",
            "2", "25.5", "-0.78423366", "12.2910249", "0.10847701",
            "2", "26.5", "-0.81409582", "12.44469258", "0.109280828",
            "2", "27.5", "-0.841935504", "12.59622335", "0.110144488",
            "2", "28.5", "-0.867889398", "12.74620911", "0.111060815",
            "2", "29.5", "-0.892102647", "12.89517218", "0.112022759",
            "2", "30.5", "-0.914718817", "13.04357164", "0.113023467",
            "2", "31.5", "-0.935876584", "13.19180874", "0.114056328",
            "2", "32.5", "-0.955723447", "13.34022934", "0.115114953",
            "2", "33.5", "-0.974383363", "13.48913319", "0.116193327",
            "2", "34.5", "-0.991980756", "13.63877446", "0.11728575",
            "2", "35.5", "-1.008640742", "13.78936547", "0.118386848",
            "2", "36.5", "-1.024471278", "13.94108332", "0.119491669",
            "2", "37.5", "-1.039573604", "14.09407175", "0.120595658",
            "2", "38.5", "-1.054039479", "14.24844498", "0.121694676",
            "2", "39.5", "-1.067946784", "14.40429169", "0.12278503",
            "2", "40.5", "-1.081374153", "14.56167529", "0.1238634",
            "2", "41.5", "-1.094381409", "14.72064045", "0.124926943",
            "2", "42.5", "-1.107021613", "14.88121352", "0.125973221",
            "2", "43.5", "-1.119338692", "15.04340553", "0.127000212",
            "2", "44.5", "-1.131367831", "15.20721443", "0.128006292",
            "2", "45.5", "-1.143135936", "15.37262729", "0.128990225",
            "2", "46.5", "-1.15466215", "15.53962221", "0.129951143",
            "2", "47.5", "-1.165958392", "15.70817017", "0.130888527",
            "2", "48.5", "-1.177029925", "15.87823668", "0.131802186",
            "2", "49.5", "-1.187871001", "16.04978452", "0.132692269",
            "2", "50.5", "-1.198484073", "16.2227706", "0.133559108",
            "2", "51.5", "-1.208853947", "16.39715363", "0.134403386",
            "2", "52.5", "-1.218965087", "16.57289122", "0.13522599",
            "2", "53.5", "-1.228798212", "16.74994187", "0.136028014",
            "2", "54.5", "-1.238330855", "16.92826587", "0.136810739",
            "2", "55.5", "-1.247537914", "17.10782615", "0.137575606",
            "2", "56.5", "-1.256392179", "17.28858894", "0.138324193",
            "2", "57.5", "-1.264864846", "17.47052444", "0.139058192",
            "2", "58.5", "-1.272926011", "17.65360733", "0.139779387",
            "2", "59.5", "-1.28054514", "17.83781722", "0.140489635",
            "2", "60.5", "-1.287691525", "18.02313904", "0.141190842",
            "2", "61.5", "-1.294332076", "18.20956418", "0.141884974",
            "2", "62.5", "-1.300441561", "18.3970876", "0.142573939",
            "2", "63.5", "-1.305989011", "18.58571243", "0.143259709",
            "2", "64.5", "-1.310946941", "18.77544728", "0.143944216",
            "2", "65.5", "-1.315289534", "18.966307", "0.144629359",
            "2", "66.5", "-1.318992925", "19.15831267", "0.14531699",
            "2", "67.5", "-1.322035315", "19.35149163", "0.146008903",
            "2", "68.5", "-1.324398133", "19.54587708", "0.146706813",
            "2", "69.5", "-1.326064539", "19.74150854", "0.147412363",
            "2", "70.5", "-1.327020415", "19.93843145", "0.148127109",
            "2", "71.5", "-1.327256387", "20.13669623", "0.148852482",
            "2", "72.5", "-1.326763834", "20.33635961", "0.149589838",
            "2", "73.5", "-1.325538668", "20.53748298", "0.1503404",
            "2", "74.5", "-1.323579654", "20.74013277", "0.151105277",
            "2", "75.5", "-1.320888012", "20.94438028", "0.151885464",
            "2", "76.5", "-1.317468695", "21.15030093", "0.152681819",
            "2", "77.5", "-1.313331446", "21.35797332", "0.15349505",
            "2", "78.5", "-1.308487081", "21.56748045", "0.154325756",
            "2", "79.5", "-1.302948173", "21.77890902", "0.155174414",
            "2", "80.5", "-1.296733913", "21.99234686", "0.15604132",
            "2", "81.5", "-1.289863329", "22.20788541", "0.156926667",
            "2", "82.5", "-1.282358762", "22.4256177", "0.157830504",
            "2", "83.5", "-1.274244931", "22.64563824", "0.158752743",
            "2", "84.5", "-1.265548787", "22.86804258", "0.159693163",
            "2", "85.5", "-1.256299378", "23.09292679", "0.16065141",
            "2", "86.5", "-1.24653066", "23.32038549", "0.161626956",
            "2", "87.5", "-1.236266832", "23.55051871", "0.162619308",
            "2", "88.5", "-1.225551344", "23.78341652", "0.1636276",
            "2", "89.5", "-1.214410914", "24.01917703", "0.1646511",
            "2", "90.5", "-1.202884389", "24.25789074", "0.165688808",
            "2", "91.5", "-1.191007906", "24.49964778", "0.166739662",
            "2", "92.5", "-1.178818621", "24.74453536", "0.167802495",
            "2", "93.5", "-1.166354376", "24.99263735", "0.168876037",
            "2", "94.5", "-1.153653688", "25.24403371", "0.169958922",
            "2", "95.5", "-1.140751404", "25.49880264", "0.171049756",
            "2", "96.5", "-1.127684095", "25.7570168", "0.172147043",
            "2", "97.5", "-1.114490244", "26.01874261", "0.173249185",
            "2", "98.5", "-1.101204848", "26.28404312", "0.174354569",
            "2", "99.5", "-1.087863413", "26.55297507", "0.175461512",
            "2", "100.5", "-1.074500927", "26.82558904", "0.176568284",
            "2", "101.5", "-1.061151213", "27.1019295", "0.177673124",
            "2", "102.5", "-1.047847141", "27.38203422", "0.178774242",
            "2", "103.5", "-1.034620551", "27.66593402", "0.179869829",
            "2", "104.5", "-1.021502197", "27.9536524", "0.180958063",
            "2", "105.5", "-1.008521695", "28.24520531", "0.182037118",
            "2", "106.5", "-0.995707494", "28.54060085", "0.183105172",
            "2", "107.5", "-0.983086844", "28.83983907", "0.18416041",
            "2", "108.5", "-0.970685789", "29.14291171", "0.185201039",
            "2", "109.5", "-0.958529157", "29.44980208", "0.186225287",
            "2", "110.5", "-0.946640568", "29.76048479", "0.187231416",
            "2", "111.5", "-0.935042447", "30.0749257", "0.188217723",
            "2", "112.5", "-0.923756041", "30.39308176", "0.18918255",
            "2", "113.5", "-0.912801445", "30.71490093", "0.190124286",
            "2", "114.5", "-0.902197638", "31.0403221", "0.191041375",
            "2", "115.5", "-0.891962513", "31.36927506", "0.191932319",
            "2", "116.5", "-0.882112919", "31.7016805", "0.192795682",
            "2", "117.5", "-0.872664706", "32.03744999", "0.193630095",
            "2", "118.5", "-0.863632768", "32.37648607", "0.19443426",
            "2", "119.5", "-0.855031092", "32.71868225", "0.195206948",
            "2", "120.5", "-0.846872805", "33.06392318", "0.195947008",
            "2", "121.5", "-0.839170224", "33.4120847", "0.196653365",
            "2", "122.5", "-0.831934903", "33.76303402", "0.197325023",
            "2", "123.5", "-0.825177688", "34.1166299", "0.197961065",
            "2", "124.5", "-0.818908758", "34.47272283", "0.198560655",
            "2", "125.5", "-0.813137675", "34.83115524", "0.199123037",
            "2", "126.5", "-0.807873433", "35.19176177", "0.199647538",
            "2", "127.5", "-0.803122613", "35.55437176", "0.200133598",
            "2", "128.5", "-0.79889771", "35.91879976", "0.200580618",
            "2", "129.5", "-0.795203499", "36.28486194", "0.200988216",
            "2", "130.5", "-0.792047959", "36.65236365", "0.201356017",
            "2", "131.5", "-0.789435274", "37.02110818", "0.201683791",
            "2", "132.5", "-0.787374433", "37.39088668", "0.201971282",
            "2", "133.5", "-0.785870695", "37.76148905", "0.202218375",
            "2", "134.5", "-0.784929893", "38.1326991", "0.202425006",
            "2", "135.5", "-0.784557605", "38.50429603", "0.202591183",
            "2", "136.5", "-0.78475917", "38.87605489", "0.20271698",
            "2", "137.5", "-0.785539703", "39.24774707", "0.202802535",
            "2", "138.5", "-0.786904102", "39.61914076", "0.202848049",
            "2", "139.5", "-0.788858208", "39.98999994", "0.202853758",
            "2", "140.5", "-0.791403051", "40.36009244", "0.202820053",
            "2", "141.5", "-0.794546352", "40.72917544", "0.202747236",
            "2", "142.5", "-0.79829102", "41.09701099", "0.202635758",
            "2", "143.5", "-0.802640891", "41.46335907", "0.202486098",
            "2", "144.5", "-0.807599577", "41.82797963", "0.202298783",
            "2", "145.5", "-0.813170461", "42.19063313", "0.202074385",
            "2", "146.5", "-0.819356692", "42.55108107", "0.201813521",
            "2", "147.5", "-0.826161176", "42.90908653", "0.201516851",
            "2", "148.5", "-0.833586038", "43.2644155", "0.201185082",
            "2", "149.5", "-0.841634949", "43.61683402", "0.200818928",
            "2", "150.5", "-0.850307441", "43.9661169", "0.200419208",
            "2", "151.5", "-0.859607525", "44.31203579", "0.199986681",
            "2", "152.5", "-0.869534339", "44.65437319", "0.199522233",
            "2", "153.5", "-0.880088651", "44.99291356", "0.199026736",
            "2", "154.5", "-0.891270585", "45.32744704", "0.198501096",
            "2", "155.5", "-0.903079458", "45.65777013", "0.197946255",
            "2", "156.5", "-0.915513542", "45.98368656", "0.197363191",
            "2", "157.5", "-0.928569454", "46.30500858", "0.196752931",
            "2", "158.5", "-0.942245864", "46.62155183", "0.196116472",
            "2", "159.5", "-0.956537923", "46.93314404", "0.19545489",
            "2", "160.5", "-0.971440492", "47.23962058", "0.194769279",
            "2", "161.5", "-0.986947308", "47.54082604", "0.194060758",
            "2", "162.5", "-1.003050887", "47.83661466", "0.193330477",
            "2", "163.5", "-1.019742425", "48.12685082", "0.192579614",
            "2", "164.5", "-1.037011698", "48.41140938", "0.191809374",
            "2", "165.5", "-1.054846957", "48.69017613", "0.191020995",
            "2", "166.5", "-1.073234825", "48.9630481", "0.190215739",
            "2", "167.5", "-1.092160195", "49.22993391", "0.189394901",
            "2", "168.5", "-1.111606122", "49.49075409", "0.188559804",
            "2", "169.5", "-1.131553723", "49.74544132", "0.187711798",
            "2", "170.5", "-1.151982079", "49.99394068", "0.186852266",
            "2", "171.5", "-1.172868141", "50.23620985", "0.185982617",
            "2", "172.5", "-1.19418462", "50.47222213", "0.185104331",
            "2", "173.5", "-1.215907492", "50.70195581", "0.184218803",
            "2", "174.5", "-1.238005268", "50.92540942", "0.183327556",
            "2", "175.5", "-1.260445591", "51.14259229", "0.182432113",
            "2", "176.5", "-1.283193626", "51.3535268", "0.181534018",
            "2", "177.5", "-1.306212032", "51.55824831", "0.180634839",
            "2", "178.5", "-1.329460945", "51.75680513", "0.179736168",
            "2", "179.5", "-1.35289798", "51.94925841", "0.178839614",
            "2", "180.5", "-1.376478254", "52.13568193", "0.177946804",
            "2", "181.5", "-1.400154426", "52.31616197", "0.177059379",
            "2", "182.5", "-1.423876772", "52.49079703", "0.17617899",
            "2", "183.5", "-1.447593267", "52.65969757", "0.175307296",
            "2", "184.5", "-1.471249702", "52.82298572", "0.174445958",
            "2", "185.5", "-1.494789826", "52.9807949", "0.173596636",
            "2", "186.5", "-1.518155513", "53.13326946", "0.172760982",
            "2", "187.5", "-1.541286949", "53.28056425", "0.17194064",
            "2", "188.5", "-1.564122852", "53.42284417", "0.171137232",
            "2", "189.5", "-1.586600712", "53.5602837", "0.170352363",
            "2", "190.5", "-1.608657054", "53.69306637", "0.169587605",
            "2", "191.5", "-1.630227728", "53.82138422", "0.168844497",
            "2", "192.5", "-1.651248208", "53.94543725", "0.168124538",
            "2", "193.5", "-1.67165392", "54.06543278", "0.167429179",
            "2", "194.5", "-1.691380583", "54.18158486", "0.166759816",
            "2", "195.5", "-1.710364557", "54.29411356", "0.166117788",
            "2", "196.5", "-1.728543207", "54.40324431", "0.165504365",
            "2", "197.5", "-1.745855274", "54.50920717", "0.164920747",
            "2", "198.5", "-1.762241248", "54.61223603", "0.164368054",
            "2", "199.5", "-1.777643747", "54.71256787", "0.16384732",
            "2", "200.5", "-1.792007891", "54.81044184", "0.163359491",
            "2", "201.5", "-1.805281675", "54.90609842", "0.162905415",
            "2", "202.5", "-1.817416335", "54.99977846", "0.162485839",
            "2", "203.5", "-1.828366707", "55.09172217", "0.162101402",
            "2", "204.5", "-1.838091576", "55.18216811", "0.161752634",
            "2", "205.5", "-1.846554015", "55.271352", "0.161439944",
            "2", "206.5", "-1.853721704", "55.35950558", "0.161163623",
            "2", "207.5", "-1.859567242", "55.44685531", "0.160923833",
            "2", "208.5", "-1.864068443", "55.53362107", "0.160720609",
            "2", "209.5", "-1.86720861", "55.62001464", "0.16055385",
            "2", "210.5", "-1.8689768", "55.70623826", "0.160423319",
            "2", "211.5", "-1.869371157", "55.79247939", "0.160328578",
            "2", "212.5", "-1.868386498", "55.87892356", "0.160269232",
            "2", "213.5", "-1.866033924", "55.96573022", "0.160244549",
            "2", "214.5", "-1.862327775", "56.05304601", "0.160253714",
            "2", "215.5", "-1.857289195", "56.14099882", "0.160295765",
            "2", "216.5", "-1.850946286", "56.22969564", "0.16036959",
            "2", "217.5", "-1.84333425", "56.3192203", "0.16047393",
            "2", "218.5", "-1.834495505", "56.40963105", "0.160607377",
            "2", "219.5", "-1.824479785", "56.50095811", "0.16076838",
            "2", "220.5", "-1.813344222", "56.59320107", "0.160955249",
            "2", "221.5", "-1.801153404", "56.68632619", "0.161166157",
            "2", "222.5", "-1.787979408", "56.78026364", "0.161399151",
            "2", "223.5", "-1.773901816", "56.87490465", "0.161652158",
            "2", "224.5", "-1.759007704", "56.97009856", "0.161922998",
            "2", "225.5", "-1.743391606", "57.06564989", "0.162209399",
            "2", "226.5", "-1.72715546", "57.16131528", "0.162509006",
            "2", "227.5", "-1.710410733", "57.25679821", "0.162819353",
            "2", "228.5", "-1.693267093", "57.35175792", "0.163138124",
            "2", "229.5", "-1.67585442", "57.44578172", "0.163462715",
            "2", "230.5", "-1.658302847", "57.53840429", "0.163790683",
            "2", "231.5", "-1.640747464", "57.62910094", "0.164119574",
            "2", "232.5", "-1.623332891", "57.7172758", "0.164446997",
            "2", "233.5", "-1.606209374", "57.80226553", "0.164770638",
            "2", "234.5", "-1.589533346", "57.88333502", "0.165088289",
            "2", "235.5", "-1.573467222", "57.95967458", "0.165397881",
            "2", "236.5", "-1.558179166", "58.0303973", "0.165697507",
            "2", "237.5", "-1.543846192", "58.09453209", "0.165985386",
            "2", "238.5", "-1.530642461", "58.15103575", "0.166260109",
            "2", "239.5", "-1.518754013", "58.1987714", "0.16652037",
            "2", "240", "-1.51336185", "58.21897289", "0.166644749");

    List<String> listTaliePtVarsta = Arrays.asList(
           // "1", "0", "1.267004226", "49.98888408", "0.053112191",
           // "1", "0", "0.511237696", "52.6959753", "0.048692684",

            "1", "1", "-0.45224446", "56.62842855", "0.04411683",
            "1", "2", "-0.990594599", "59.60895343", "0.041795583",
            "1", "3", "-1.285837689", "62.07700027", "0.040454126",
            "1", "4", "-1.43031238", "64.2168641", "0.039633879",
            "1", "5", "-1.47657547", "66.1253149", "0.039123813",
            "1", "6", "-1.456837849", "67.8601799", "0.038811994",
            "1", "7", "-1.391898768", "69.45908458", "0.038633209",
            "1", "8", "-1.29571459", "70.94803912", "0.038546833",
            "1", "9", "-1.177919048", "72.34586111", "0.038526262",
            "1", "10", "-1.045326049", "73.6666541", "0.038553387",
            "1", "11", "-0.902800887", "74.92129717", "0.038615501",
            "1", "12", "-0.753908107", "76.11837536", "0.038703461",
            "1", "13", "-0.601263523", "77.26479911", "0.038810557",
            "1", "14", "-0.446805039", "78.36622309", "0.038931784",
            "1", "15", "-0.291974772", "79.4273405", "0.039063356",
            "1", "16", "-0.13784767", "80.45209492", "0.039202382",
            "1", "17", "0.014776155", "81.44383603", "0.039346629",
            "1", "18", "0.165304169", "82.40543643", "0.039494365",
            "1", "19", "0.313301809", "83.33938063", "0.039644238",
            "1", "20", "0.458455471", "84.24783394", "0.039795189",
            "1", "21", "0.600544631", "85.13269658", "0.039946388",
            "1", "22", "0.739438953", "85.9956488", "0.040097181",
            "1", "23", "0.875000447", "86.8381751", "0.04024706",
            "2", "0", "-1.295960857", "49.28639612", "0.05008556",
            "2", "0", "-0.809249882", "51.68358057", "0.046818545",
            "2", "1", "-0.050782985", "55.28612813", "0.0434439",
            "2", "2", "0.476851407", "58.09381906", "0.041716103",
            "2", "3", "0.843299612", "60.45980763", "0.040705173",
            "2", "4", "1.097562257", "62.53669656", "0.040079765",
            "2", "5", "1.272509641", "64.40632762", "0.039686845",
            "2", "6", "1.390428859", "66.11841553", "0.039444555",
            "2", "7", "1.466733925", "67.70574419", "0.039304738",
            "2", "8", "1.512301976", "69.19123614", "0.03923711",
            "2", "9", "1.534950767", "70.59163924", "0.039221665",
            "2", "10", "1.540390875", "71.91961673", "0.039244672",
            "2", "11", "1.532852892", "73.1850104", "0.03929642",
            "2", "12", "1.51550947", "74.39564379", "0.039369875",
            "2", "13", "1.490765028", "75.5578544", "0.039459832",
            "2", "14", "1.460458255", "76.67685871", "0.039562382",
            "2", "15", "1.426006009", "77.75700986", "0.039674542",
            "2", "16", "1.388507095", "78.80198406", "0.03979401",
            "2", "17", "1.348818127", "79.81491852", "0.039918994",
            "2", "18", "1.307609654", "80.79851532", "0.040048084",
            "2", "19", "1.265408149", "81.75512092", "0.040180162",
            "2", "20", "1.222627732", "82.6867881", "0.04031434",
            "2", "21", "1.179594365", "83.59532461", "0.040449904",
            "2", "22", "1.136564448", "84.48233206", "0.040586283",
            "2", "23", "1.093731947", "85.34923624", "0.040723015",
            "1", "24", "1.00720807", "86.86160934", "0.040395626",
            "1", "25", "0.837251351", "87.65247282", "0.040577525",
            "1", "26", "0.681492975", "88.42326434", "0.040723122",
            "1", "27", "0.538779654", "89.17549228", "0.040833194",
            "1", "28", "0.407697153", "89.91040853", "0.040909059",
            "1", "29", "0.286762453", "90.62907762", "0.040952433",
            "1", "30", "0.174489485", "91.33242379", "0.04096533",
            "1", "31", "0.069444521", "92.02127167", "0.040949976",
            "1", "32", "-0.029720564", "92.69637946", "0.040908737",
            "1", "33", "-0.124251789", "93.35846546", "0.040844062",
            "1", "34", "-0.215288396", "94.00822923", "0.040758431",
            "1", "35", "-0.30385434", "94.64636981", "0.040654312",
            "1", "36", "-0.390918369", "95.27359106", "0.04053412",
            "1", "37", "-0.254801167", "95.91474929", "0.040572876",
            "1", "38", "-0.125654535", "96.54734328", "0.04061691",
            "1", "39", "-0.00316735", "97.17191309", "0.040666414",
            "1", "40", "0.11291221", "97.78897727", "0.040721467",
            "1", "41", "0.222754969", "98.3990283", "0.040782045",
            "1", "42", "0.326530126", "99.00254338", "0.040848042",
            "1", "43", "0.42436156", "99.599977", "0.040919281",
            "1", "44", "0.516353108", "100.191764", "0.040995524",
            "1", "45", "0.602595306", "100.7783198", "0.041076485",
            "1", "46", "0.683170764", "101.3600411", "0.041161838",
            "1", "47", "0.758158406", "101.9373058", "0.041251224",
            "1", "48", "0.827636736", "102.5104735", "0.041344257",
            "1", "49", "0.891686306", "103.0798852", "0.041440534",
            "1", "50", "0.95039153", "103.645864", "0.041539635",
            "1", "51", "1.003830006", "104.208713", "0.041641136",
            "1", "52", "1.05213569", "104.7687256", "0.041744602",
            "1", "53", "1.0953669", "105.3261638", "0.041849607",
            "1", "54", "1.133652119", "105.8812823", "0.041955723",
            "1", "55", "1.167104213", "106.4343146", "0.042062532",
            "1", "56", "1.195845353", "106.9854769", "0.042169628",
            "1", "57", "1.220004233", "107.534968", "0.042276619",
            "1", "58", "1.239715856", "108.0829695", "0.042383129",
            "1", "59", "1.255121285", "108.6296457", "0.042488804",
            "1", "60", "1.266367398", "109.1751441", "0.042593311",
            "1", "61", "1.273606657", "109.7195954", "0.042696342",
            "1", "62", "1.276996893", "110.2631136", "0.042797615",
            "1", "63", "1.276701119", "110.8057967", "0.042896877",
            "1", "64", "1.272887366", "111.3477265", "0.042993904",
            "1", "65", "1.265728536", "111.8889694", "0.043088503",
            "1", "66", "1.255402281", "112.4295761", "0.043180513",
            "1", "67", "1.242090871", "112.9695827", "0.043269806",
            "1", "68", "1.225981067", "113.5090108", "0.043356287",
            "1", "69", "1.207263978", "114.0478678", "0.043439893",
            "1", "70", "1.186140222", "114.5861486", "0.043520597",
            "1", "71", "1.162796198", "115.1238315", "0.043598407",
            "1", "72", "1.137442868", "115.6608862", "0.043673359",
            "1", "73", "1.110286487", "116.1972691", "0.043745523",
            "1", "74", "1.081536236", "116.732925", "0.043815003",
            "1", "75", "1.05140374", "117.2677879", "0.043881929",
            "1", "76", "1.020102497", "117.8017819", "0.043946461",
            "1", "77", "0.987847213", "118.3348215", "0.044008785",
            "1", "78", "0.954853043", "118.8668123", "0.044069112",
            "1", "79", "0.921334742", "119.397652", "0.044127675",
            "1", "80", "0.887505723", "119.9272309", "0.044184725",
            "1", "81", "0.85357703", "120.455433", "0.044240532",
            "1", "82", "0.819756239", "120.9821362", "0.044295379",
            "1", "83", "0.786246296", "121.5072136", "0.044349559",
            "1", "84", "0.753244292", "122.0305342", "0.044403374",
            "1", "85", "0.720940222", "122.5519634", "0.04445713",
            "1", "86", "0.689515708", "123.0713645", "0.044511135",
            "1", "87", "0.659142731", "123.588599", "0.044565693",
            "1", "88", "0.629997853", "124.1035312", "0.044621104",
            "1", "89", "0.602203984", "124.6160161", "0.044677662",
            "1", "90", "0.575908038", "125.1259182", "0.044735646",
            "1", "91", "0.55123134", "125.6331012", "0.044795322",
            "1", "92", "0.528279901", "126.1374319", "0.044856941",
            "1", "93", "0.507143576", "126.6387804", "0.04492073",
            "1", "94", "0.487895344", "127.1370217", "0.044986899",
            "1", "95", "0.470590753", "127.6320362", "0.045055632",
            "1", "96", "0.455267507", "128.1237104", "0.045127088",
            "1", "97", "0.441945241", "128.6119383", "0.045201399",
            "1", "98", "0.430625458", "129.096622", "0.045278671",
            "1", "99", "0.421291648", "129.5776723", "0.045358979",
            "1", "100", "0.413909588", "130.0550101", "0.045442372",
            "1", "101", "0.408427813", "130.5285669", "0.045528869",
            "1", "102", "0.404778262", "130.9982857", "0.045618459",
            "1", "103", "0.402877077", "131.4641218", "0.045711105",
            "1", "104", "0.402625561", "131.9260439", "0.045806742",
            "1", "105", "0.40391127", "132.3840348", "0.045905281",
            "1", "106", "0.406609232", "132.838092", "0.046006604",
            "1", "107", "0.410583274", "133.2882291", "0.046110573",
            "1", "108", "0.415687443", "133.7344759", "0.046217028",
            "1", "109", "0.421767514", "134.1768801", "0.04632579",
            "1", "110", "0.428662551", "134.6155076", "0.046436662",
            "1", "111", "0.436206531", "135.0504433", "0.04654943",
            "1", "112", "0.44423", "135.4817925", "0.046663871",
            "1", "113", "0.45256176", "135.9096813", "0.046779748",
            "1", "114", "0.461030578", "136.3342577", "0.046896817",
            "1", "115", "0.469466904", "136.7556923", "0.047014827",
            "1", "116", "0.477704608", "137.1741794", "0.047133525",
            "1", "117", "0.48558272", "137.5899378", "0.047252654",
            "1", "118", "0.492947182", "138.0032114", "0.047371961",
            "1", "119", "0.499652617", "138.4142703", "0.047491194",
            "1", "120", "0.505564115", "138.8234114", "0.047610108",
            "1", "121", "0.510559047", "139.2309592", "0.047728463",
            "1", "122", "0.514528903", "139.6372663", "0.04784603",
            "1", "123", "0.517381177", "140.042714", "0.047962592",
            "1", "124", "0.519041285", "140.4477127", "0.048077942",
            "1", "125", "0.519454524", "140.8527022", "0.048191889",
            "1", "126", "0.518588072", "141.2581515", "0.048304259",
            "1", "127", "0.516433004", "141.6645592", "0.048414893",
            "1", "128", "0.513006312", "142.072452", "0.048523648",
            "1", "129", "0.508352901", "142.4823852", "0.048630402",
            "1", "130", "0.502547502", "142.8949403", "0.04873505",
            "1", "131", "0.495696454", "143.3107241", "0.048837504",
            "1", "132", "0.487939275", "143.7303663", "0.048937694",
            "1", "133", "0.479449924", "144.1545167", "0.049035564",
            "1", "134", "0.470437652", "144.5838414", "0.049131073",
            "1", "135", "0.461147305", "145.0190192", "0.049224189",
            "1", "136", "0.451858946", "145.4607359", "0.049314887",
            "1", "137", "0.442886661", "145.9096784", "0.049403145",
            "1", "138", "0.434576385", "146.3665278", "0.049488934",
            "1", "139", "0.427302633", "146.8319513", "0.049572216",
            "1", "140", "0.421464027", "147.3065929", "0.049652935",
            "1", "141", "0.417477538", "147.7910635", "0.049731004",
            "1", "142", "0.415771438", "148.2859294", "0.0498063",
            "1", "143", "0.416777012", "148.7917006", "0.04987865",
            "1", "144", "0.420919142", "149.3088178", "0.049947823",
            "1", "145", "0.428606007", "149.8376391", "0.050013518",
            "1", "146", "0.440218167", "150.3784267", "0.050075353",
            "1", "147", "0.456097443", "150.9313331", "0.050132858",
            "1", "148", "0.476536014", "151.4963887", "0.050185471",
            "1", "149", "0.501766234", "152.0734897", "0.050232532",
            "1", "150", "0.531951655", "152.6623878", "0.050273285",
            "1", "151", "0.567179725", "153.2626819", "0.050306885",
            "1", "152", "0.607456565", "153.8738124", "0.050332406",
            "1", "153", "0.652704121", "154.495058", "0.05034886",
            "1", "154", "0.702759868", "155.1255365", "0.050355216",
            "1", "155", "0.757379106", "155.7642086", "0.050350423",
            "1", "156", "0.816239713", "156.4098858", "0.050333444",
            "1", "157", "0.878947416", "157.0612415", "0.050303283",
            "1", "158", "0.945053486", "157.7168289", "0.050259018",
            "1", "159", "1.014046108", "158.3750929", "0.050199837",
            "1", "160", "1.085383319", "159.034399", "0.050125062",
            "1", "161", "1.158487278", "159.6930501", "0.05003418",
            "1", "162", "1.232768816", "160.3493168", "0.049926861",
            "1", "163", "1.307628899", "161.0014586", "0.049802977",
            "1", "164", "1.382473225", "161.6477515", "0.04966261",
            "1", "165", "1.456720479", "162.2865119", "0.049506051",
            "1", "166", "1.529810247", "162.9161202", "0.049333801",
            "1", "167", "1.601219573", "163.535045", "0.049146553",
            "1", "168", "1.670433444", "164.1418486", "0.04894519",
            "1", "169", "1.736995571", "164.7352199", "0.048730749",
            "1", "170", "1.800483802", "165.3139755", "0.048504404",
            "1", "171", "1.860518777", "165.8770715", "0.048267442",
            "1", "172", "1.916765525", "166.4236087", "0.04802123",
            "1", "173", "1.968934444", "166.9528354", "0.047767192",
            "1", "174", "2.016781776", "167.4641466", "0.047506783",
            "1", "175", "2.060109658", "167.9570814", "0.047241456",
            "1", "176", "2.098765817", "168.4313175", "0.04697265",
            "1", "177", "2.132642948", "168.8866644", "0.046701759",
            "1", "178", "2.16167779", "169.3230548", "0.046430122",
            "1", "179", "2.185849904", "169.7405351", "0.046159004",
            "1", "180", "2.205180153", "170.139255", "0.045889585",
            "1", "181", "2.219728869", "170.5194567", "0.045622955",
            "1", "182", "2.2295937", "170.881464", "0.045360101",
            "1", "183", "2.234907144", "171.2256717", "0.045101913",
            "1", "184", "2.235833767", "171.5525345", "0.044849174",
            "1", "185", "2.232567138", "171.8625576", "0.044602566",
            "1", "186", "2.2253265", "172.1562865", "0.044362674",
            "1", "187", "2.214353232", "172.4342983", "0.044129985",
            "1", "188", "2.199905902", "172.6971935", "0.043904897",
            "1", "189", "2.182262864", "172.9455898", "0.043687723",
            "1", "190", "2.161704969", "173.180112", "0.043478698",
            "1", "191", "2.138524662", "173.4013896", "0.043277987",
            "1", "192", "2.113023423", "173.6100518", "0.043085685",
            "1", "193", "2.085490286", "173.8067179", "0.042901835",
            "1", "194", "2.0562195", "173.9919998", "0.042726424",
            "1", "195", "2.025496648", "174.1664951", "0.042559396",
            "1", "196", "1.993598182", "174.3307855", "0.042400652",
            "1", "197", "1.960789092", "174.4854344", "0.042250063",
            "1", "198", "1.927320937", "174.6309856", "0.042107465",
            "1", "199", "1.89343024", "174.7679617", "0.041972676",
            "1", "200", "1.859337259", "174.8968634", "0.041845488",
            "1", "201", "1.825245107", "175.0181691", "0.041725679",
            "1", "202", "1.791339209", "175.1323345", "0.041613015",
            "1", "203", "1.757787065", "175.2397926", "0.041507249",
            "1", "204", "1.724738292", "175.340954", "0.041408129",
            "1", "205", "1.692324905", "175.4362071", "0.041315398",
            "1", "206", "1.660661815", "175.5259191", "0.041228796",
            "1", "207", "1.629847495", "175.6104358", "0.04114806",
            "1", "208", "1.599964788", "175.690083", "0.041072931",
            "1", "209", "1.571081817", "175.7651671", "0.04100315",
            "1", "210", "1.543252982", "175.8359757", "0.040938463",
            "1", "211", "1.516519998", "175.9027788", "0.040878617",
            "1", "212", "1.490912963", "175.9658293", "0.040823368",
            "1", "213", "1.466451429", "176.0253641", "0.040772475",
            "1", "214", "1.44314546", "176.081605", "0.040725706",
            "1", "215", "1.420996665", "176.1347593", "0.040682834",
            "1", "216", "1.399999187", "176.1850208", "0.04064364",
            "1", "217", "1.380140651", "176.2325707", "0.040607913",
            "1", "218", "1.361403047", "176.2775781", "0.040575448",
            "1", "219", "1.343763564", "176.3202008", "0.040546051",
            "1", "220", "1.327195355", "176.3605864", "0.040519532",
            "1", "221", "1.311668242", "176.3988725", "0.040495713",
            "1", "222", "1.297149359", "176.4351874", "0.040474421",
            "1", "223", "1.283603728", "176.469651", "0.040455493",
            "1", "224", "1.270994782", "176.5023751", "0.040438773",
            "1", "225", "1.25928483", "176.533464", "0.040424111",
            "1", "226", "1.248435461", "176.5630153", "0.040411366",
            "1", "227", "1.23840791", "176.5911197", "0.040400405",
            "1", "228", "1.229163362", "176.6178621", "0.040391101",
            "1", "229", "1.220663228", "176.6433219", "0.040383334",
            "1", "230", "1.212869374", "176.6675729", "0.04037699",
            "1", "231", "1.20574431", "176.6906844", "0.040371962",
            "1", "232", "1.199251356", "176.712721", "0.040368149",
            "1", "233", "1.19335477", "176.733743", "0.040365456",
            "1", "234", "1.188019859", "176.753807", "0.040363795",
            "1", "235", "1.183213059", "176.7729657", "0.04036308",
            "1", "236", "1.178901998", "176.7912687", "0.040363233",
            "1", "237", "1.175055543", "176.8087622", "0.040364179",
            "1", "238", "1.171643828", "176.8254895", "0.04036585",
            "1", "239", "1.16863827", "176.8414914", "0.04036818",
            "1", "240", "1.167279219", "176.8492322", "0.040369574",
            "2", "24", "1.07244896", "84.97555512", "0.040791394",
            "2", "24", "1.051272912", "85.3973169", "0.040859727",
            "2", "25", "1.041951175", "86.29026318", "0.041142161",
            "2", "26", "1.012592236", "87.15714182", "0.041349399",
            "2", "27", "0.970541909", "87.9960184", "0.041500428",
            "2", "28", "0.921129988", "88.8055115", "0.041610508",
            "2", "29", "0.868221392", "89.58476689", "0.041691761",
            "2", "30", "0.81454413", "90.33341722", "0.04175368",
            "2", "31", "0.761957977", "91.0515436", "0.041803562",
            "2", "32", "0.711660228", "91.7396352", "0.041846882",
            "2", "33", "0.664323379", "92.39854429", "0.041887626",
            "2", "34", "0.620285102", "93.02945392", "0.041928568",
            "2", "35", "0.57955631", "93.63382278", "0.041971514",
            "2", "36", "0.54198094", "94.21335709", "0.042017509",
            "2", "37", "0.511429832", "94.79643239", "0.042104522",
            "2", "38", "0.482799937", "95.37391918", "0.042199507",
            "2", "39", "0.455521041", "95.94692677", "0.042300333",
            "2", "40", "0.429150288", "96.51644912", "0.042405225",
            "2", "41", "0.403351725", "97.08337211", "0.042512706",
            "2", "42", "0.377878239", "97.6484807", "0.042621565",
            "2", "43", "0.352555862", "98.21246579", "0.042730809",
            "2", "44", "0.327270297", "98.77593069", "0.042839638",
            "2", "45", "0.301955463", "99.33939735", "0.042947412",
            "2", "46", "0.276583851", "99.9033122", "0.043053626",
            "2", "47", "0.251158446", "100.4680516", "0.043157889",
            "2", "48", "0.225705996", "101.033927", "0.043259907",
            "2", "49", "0.20027145", "101.6011898", "0.043359463",
            "2", "50", "0.174913356", "102.1700358", "0.043456406",
            "2", "51", "0.149700081", "102.7406094", "0.043550638",
            "2", "52", "0.12470671", "103.3130077", "0.043642107",
            "2", "53", "0.100012514", "103.8872839", "0.043730791",
            "2", "54", "0.075698881", "104.4634511", "0.043816701",
            "2", "55", "0.051847635", "105.0414853", "0.043899867",
            "2", "56", "0.02853967", "105.6213287", "0.043980337",
            "2", "57", "0.005853853", "106.2028921", "0.044058171",
            "2", "58", "-0.016133871", "106.7860583", "0.04413344",
            "2", "59", "-0.037351181", "107.3706841", "0.044206218",
            "2", "60", "-0.057729947", "107.9566031", "0.044276588",
            "2", "61", "-0.077206672", "108.5436278", "0.044344632",
            "2", "62", "-0.09572283", "109.1315521", "0.044410436",
            "2", "63", "-0.113225128", "109.7201531", "0.044474084",
            "2", "64", "-0.129665689", "110.3091934", "0.044535662",
            "2", "65", "-0.145002179", "110.8984228", "0.044595254",
            "2", "66", "-0.159197885", "111.4875806", "0.044652942",
            "2", "67", "-0.172221748", "112.0763967", "0.044708809",
            "2", "68", "-0.184048358", "112.6645943", "0.044762936",
            "2", "69", "-0.194660215", "113.2518902", "0.044815402",
            "2", "70", "-0.204030559", "113.8380006", "0.044866288",
            "2", "71", "-0.212174408", "114.4226317", "0.044915672",
            "2", "72", "-0.219069129", "115.0054978", "0.044963636",
            "2", "73", "-0.224722166", "115.5863089", "0.045010259",
            "2", "74", "-0.229140412", "116.1647782", "0.045055624",
            "2", "75", "-0.232335686", "116.7406221", "0.045099817",
            "2", "76", "-0.234324563", "117.3135622", "0.045142924",
            "2", "77", "-0.235128195", "117.8833259", "0.045185036",
            "2", "78", "-0.234772114", "118.4496481", "0.045226249",
            "2", "79", "-0.233286033", "119.0122722", "0.045266662",
            "2", "80", "-0.230703633", "119.5709513", "0.045306383",
            "2", "81", "-0.227062344", "120.1254495", "0.045345524",
            "2", "82", "-0.222403111", "120.6755427", "0.045384203",
            "2", "83", "-0.216770161", "121.22102", "0.045422551",
            "2", "84", "-0.210210748", "121.7616844", "0.045460702",
            "2", "85", "-0.202774891", "122.2973542", "0.045498803",
            "2", "86", "-0.194515104", "122.827864", "0.045537012",
            "2", "87", "-0.185486099", "123.3530652", "0.045575495",
            "2", "88", "-0.175744476", "123.8728276", "0.045614432",
            "2", "89", "-0.165348396", "124.38704", "0.045654016",
            "2", "90", "-0.15435722", "124.8956114", "0.04569445",
            "2", "91", "-0.142831123", "125.398472", "0.045735953",
            "2", "92", "-0.130830669", "125.895574", "0.045778759",
            "2", "93", "-0.118416354", "126.3868929", "0.045823114",
            "2", "94", "-0.105648092", "126.8724284", "0.04586928",
            "2", "95", "-0.092584657", "127.3522056", "0.045917535",
            "2", "96", "-0.079283065", "127.8262759", "0.045968169",
            "2", "97", "-0.065797888", "128.2947187", "0.04602149",
            "2", "98", "-0.0521805", "128.757642", "0.046077818",
            "2", "99", "-0.03847825", "129.2151839", "0.046137487",
            "2", "100", "-0.024733545", "129.6675143", "0.046200842",
            "2", "101", "-0.010982868", "130.1148354", "0.04626824",
            "2", "102", "0.002744306", "130.5573839", "0.046340046",
            "2", "103", "0.016426655", "130.995432", "0.046416629",
            "2", "104", "0.030052231", "131.4292887", "0.046498361",
            "2", "105", "0.043619747", "131.8593015", "0.046585611",
            "2", "106", "0.05713988", "132.2858574", "0.046678741",
            "2", "107", "0.070636605", "132.7093845", "0.046778099",
            "2", "108", "0.08414848", "133.1303527", "0.04688401",
            "2", "109", "0.097729873", "133.5492749", "0.046996769",

            "2", "110", "0.111452039", "133.9667073", "0.047116633",
            "2", "111", "0.125404005", "134.3832499", "0.047243801",
            "2", "112", "0.13969316", "134.7995463", "0.047378413",
            "2", "113", "0.154445482", "135.2162826", "0.047520521",
            "2", "114", "0.169805275", "135.634186", "0.047670085",
            "2", "115", "0.185934346", "136.0540223", "0.047826946",
            "2", "116", "0.203010488", "136.4765925", "0.04799081",
            "2", "117", "0.2212252", "136.9027281", "0.048161228",
            "2", "118", "0.240780542", "137.3332846", "0.04833757",
            "2", "119", "0.261885086", "137.7691339", "0.048519011",
            "2", "120", "0.284748919", "138.2111552", "0.048704503",
            "2", "121", "0.309577733", "138.6602228", "0.048892759",
            "2", "122", "0.336566048", "139.1171933", "0.049082239",
            "2", "123", "0.365889711", "139.5828898", "0.049271137",
            "2", "124", "0.397699038", "140.0580848", "0.049457371",
            "2", "125", "0.432104409", "140.5434787", "0.049638596",
            "2", "126", "0.46917993", "141.0396832", "0.049812203",
            "2", "127", "0.508943272", "141.5471945", "0.049975355",
            "2", "128", "0.551354277", "142.0663731", "0.050125012",
            "2", "129", "0.596307363", "142.59742", "0.050257992",
            "2", "130", "0.643626542", "143.1403553", "0.050371024",
            "2", "131", "0.693062173", "143.6949981", "0.050460835",
            "2", "132", "0.744289752", "144.2609497", "0.050524236",
            "2", "133", "0.79691098", "144.8375809", "0.050558224",
            "2", "134", "0.85045728", "145.4240246", "0.050560083",
            "2", "135", "0.904395871", "146.0191748", "0.050527494",
            "2", "136", "0.958138449", "146.621692", "0.050458634",
            "2", "137", "1.011054559", "147.2300177", "0.050352269",
            "2", "138", "1.062474568", "147.8423918", "0.050207825",
            "2", "139", "1.111727029", "148.4568879", "0.050025434",
            "2", "140", "1.158135105", "149.0714413", "0.049805967",
            "2", "141", "1.201050821", "149.6838943", "0.049551023",
            "2", "142", "1.239852328", "150.2920328", "0.049262895",
            "2", "143", "1.274006058", "150.8936469", "0.048944504",
            "2", "144", "1.303044695", "151.4865636", "0.048599314",
            "2", "145", "1.326605954", "152.0686985", "0.048231224",
            "2", "146", "1.344443447", "152.6380955", "0.047844442",
            "2", "147", "1.356437773", "153.1929631", "0.047443362",
            "2", "148", "1.362602695", "153.7317031", "0.04703243",
            "2", "149", "1.363085725", "154.2529332", "0.046616026",
            "2", "150", "1.358162799", "154.755501", "0.046198356",
            "2", "151", "1.348227142", "155.2384904", "0.04578335",
            "2", "152", "1.333772923", "155.7012216", "0.045374597",
            "2", "153", "1.315374704", "156.1432438", "0.044975281",
            "2", "154", "1.293664024", "156.564323", "0.044588148",
            "2", "155", "1.269304678", "156.9644258", "0.044215488",
            "2", "156", "1.242968236", "157.3436995", "0.043859135",
            "2", "157", "1.21531127", "157.7024507", "0.04352048",
            "2", "158", "1.186955477", "158.0411233", "0.043200497",
            "2", "159", "1.158471522", "158.3602756", "0.042899776",
            "2", "160", "1.130367088", "158.6605588", "0.042618565",
            "2", "161", "1.103079209", "158.9426964", "0.042356812",
            "2", "162", "1.076970655", "159.2074654", "0.042114211",
            "2", "163", "1.052329922", "159.455679", "0.041890247",
            "2", "164", "1.029374161", "159.688172", "0.04168424",
            "2", "165", "1.008254396", "159.9057871", "0.041495379",
            "2", "166", "0.989062282", "160.1093647", "0.041322765",
            "2", "167", "0.971837799", "160.299733", "0.041165437",
            "2", "168", "0.95657215", "160.4776996", "0.041022401",
            "2", "169", "0.94324228", "160.6440526", "0.040892651",
            "2", "170", "0.931767062", "160.7995428", "0.040775193",
            "2", "171", "0.922058291", "160.9448916", "0.040669052",
            "2", "172", "0.914012643", "161.0807857", "0.040573288",
            "2", "173", "0.907516917", "161.2078755", "0.040487005",
            "2", "174", "0.902452436", "161.3267744", "0.040409354",
            "2", "175", "0.898698641", "161.4380593", "0.040339537",
            "2", "176", "0.896143482", "161.5422726", "0.040276811",
            "2", "177", "0.894659668", "161.639917", "0.040220488",
            "2", "178", "0.89413892", "161.7314645", "0.040169932",
            "2", "179", "0.894475371", "161.8173534", "0.040124562",
            "2", "180", "0.895569834", "161.8979913", "0.040083845",
            "2", "181", "0.897330209", "161.9737558", "0.040047295",
            "2", "182", "0.899671635", "162.0449969", "0.040014473",
            "2", "183", "0.902516442", "162.1120386", "0.03998498",
            "2", "184", "0.905793969", "162.17518", "0.039958458",
            "2", "185", "0.909440266", "162.2346979", "0.039934584",
            "2", "186", "0.913397733", "162.2908474", "0.039913066",
            "2", "187", "0.91761471", "162.343864", "0.039893644",
            "2", "188", "0.922045055", "162.3939652", "0.039876087",
            "2", "189", "0.926647697", "162.4413513", "0.039860185",
            "2", "190", "0.931386217", "162.4862071", "0.039845754",
            "2", "191", "0.93622842", "162.5287029", "0.039832629",
            "2", "192", "0.941145943", "162.5689958", "0.039820663",
            "2", "193", "0.94611388", "162.6072309", "0.039809725",
            "2", "194", "0.95111043", "162.6435418", "0.0397997",
            "2", "195", "0.956116576", "162.6780519", "0.039790485",
            "2", "196", "0.961115792", "162.7108751", "0.039781991",
            "2", "197", "0.966093766", "162.7421168", "0.039774136",
            "2", "198", "0.971038162", "162.7718741", "0.03976685",
            "2", "199", "0.975938391", "162.8002371", "0.03976007",
            "2", "200", "0.980785418", "162.8272889", "0.039753741",
            "2", "201", "0.985571579", "162.8531067", "0.039747815",
            "2", "202", "0.99029042", "162.8777619", "0.039742249",
            "2", "203", "0.994936555", "162.9013208", "0.039737004",
            "2", "204", "0.999505539", "162.9238449", "0.039732048",
            "2", "205", "1.003993753", "162.9453912", "0.039727352",
            "2", "206", "1.0083983", "162.9660131", "0.03972289",
            "2", "207", "1.012716921", "162.9857599", "0.03971864",
            "2", "208", "1.016947912", "163.0046776", "0.039714581",
            "2", "209", "1.021090055", "163.0228094", "0.039710697",
            "2", "210", "1.025142554", "163.0401953", "0.039706971",
            "2", "211", "1.029104983", "163.0568727", "0.039703391",
            "2", "212", "1.032977233", "163.0728768", "0.039699945",
            "2", "213", "1.036759475", "163.0882404", "0.039696623",
            "2", "214", "1.040452117", "163.1029943", "0.039693415",
            "2", "215", "1.044055774", "163.1171673", "0.039690313",
            "2", "216", "1.047571238", "163.1307866", "0.039687311",
            "2", "217", "1.050999451", "163.1438776", "0.039684402",
            "2", "218", "1.054341482", "163.1564644", "0.039681581",
            "2", "219", "1.057598512", "163.1685697", "0.039678842",
            "2", "220", "1.060771808", "163.1802146", "0.039676182",
            "2", "221", "1.063862715", "163.1914194", "0.039673596",
            "2", "222", "1.066872639", "163.202203", "0.039671082",
            "2", "223", "1.069803036", "163.2125835", "0.039668635",
            "2", "224", "1.072655401", "163.2225779", "0.039666254",
            "2", "225", "1.075431258", "163.2322024", "0.039663936",
            "2", "226", "1.078132156", "163.2414722", "0.039661679",
            "2", "227", "1.080759655", "163.2504019", "0.039659481",
            "2", "228", "1.083315329", "163.2590052", "0.039657339",
            "2", "229", "1.085800751", "163.2672954", "0.039655252",
            "2", "230", "1.088217496", "163.2752848", "0.039653218",
            "2", "231", "1.090567133", "163.2829854", "0.039651237",
            "2", "232", "1.092851222", "163.2904086", "0.039649306",
            "2", "233", "1.095071313", "163.297565", "0.039647424",
            "2", "234", "1.097228939", "163.304465", "0.039645591",
            "2", "235", "1.099325619", "163.3111185", "0.039643804",
            "2", "236", "1.101362852", "163.3175349", "0.039642063",
            "2", "237", "1.103342119", "163.3237231", "0.039640367",
            "2", "238", "1.105264876", "163.3296918", "0.039638715",
            "2", "239", "1.107132561", "163.3354491", "0.039637105",
            "2", "240", "1.108046193", "163.338251", "0.039636316");

    List<String> listHeightRo  = Arrays.asList(
        //    "0","49.06","1.8","3.28","50.2","2","3.39",

            "1","52.40","1.9","3.75","53.2","2","3.96",
            "2","55.6","2","4.6","56.70","2.1","4.9",
            "3","58.3","2","5.35","59.9","2.2","5.75",
            "4","61","2.1","6","62.5","2.2","6.48",
            "5","63","2.2","6.6","64.7","2.2","7.10",
            "6","64.8","2.2","7.11","66.4","2.3","7.58",
            "7","66.4","2.2","7.6","68","2.3","8.08",
            "8","67.8","2.3","8.05","69.5","2.3","8.5",
            "9","69.1","2.3","8.39","70.8","2.3","8.88",
            "10","70.3","2.3","8.72","72","2.3","9.2",
            "11","71.5","2.4","9","73.2","2.4","9.52",
            "12","72.6","2.5","9.24","74.3","2.4","9.81",
            "13","73.7","2.5","9.48","75.4","2.4","10.08",
            "14","74.8","2.6","9.71","76.5","2.5","10.33",
            "15","75.9","2.7","9.93","77.6","2.6","10.55",
            "16","77","2.7","10.15","78.5","2.6","10.75",
            "17","78","2.8","10.35","79.6","2.7","10.95",
            "18","79","2.9","10.56","80.5","2.7","11.14",
            "19","79.9","2.9","10.74","81.4","2.8","11.31",
            "20","80.80","3","10.91","82.3","2.8","11.48",
            "21","81.7","3","11.08","83.2","2.9","11.65",
            "22","82.6","3","11.25","84","2.9","11.82",
            "23","83.5","3.1","11.42","84.8","2.9","12",
            "24","84.3","3.1","11.59","85.6","3","12.18",
            "25","85.1","3.1","11.77","86.4","3","12.36",
            "26","85.9","3.1","11.95","87.2","3","12.54",
            "27","86.7","3.1","12.13","88","3.1","12.71",
            "28","87.4","3.2","12.30","88.8","3.1","12.88",
            "29","88.1","3.2","12.47","89.5","3.2","13.05",
            "30","88.8","3.2","12.64","90.2","3.2","13.22",
            "31","89.5","3.2","12.8","90.9","3.2","13.38",
            "32","90.2","3.2","12.96","91.6","3.3","13.53",
            "33","90.90","3.2","13.12","92.3","3.3","13.68",
            "34","91.5","3.3","13.28","93","3.4","13.83",
            "35","92.1","3.3","13.44","93.6","3.4","13.98",
            "36","92.7","3.3","13.6","94.2","3.5","14.14",
            "37","93.4","3.3","13.76","94.9","3.5","14.30",
            "38","94","3.3","13.92","95.5","3.5","14.47",
            "39","94.6","3.4","14.07","96.1","3.6","14.64",
            "40","95.2","3.4","14.22","96.7","3.6","14.81",
            "41","95.8","3.4","14.37","97.3","3.7","14.98",
            "42","96.4","3.5","14.52","97.9","3.7","15.15",
            "43","97","3.5","14.66","98.5","3.7","15.30",
            "44","97.6","3.5","14.80","99.1","3.7","15.45",
            "45","98.2","3.5","14.94","99.7","3.8","15.60",
            "46","98.8","3.6","15.08","100.3","3.8","15.74",
            "47","99.3","3.6","15.22","100.8","3.8","15.88",
            "48","99.8","3.6","15.36","101.3","3.9","16.02",
            "49","100.4","3.6","15.52","101.8","3.9","16.17",
            "50","101","3.7","15.68","102.3","3.9","16.32",
            "51","101.6","3.7","15.84","102.8","3.9","16.47",
            "52","102.1","3.7","16","103.3","4","16.63",
            "53","102.6","3.8","16.16","103.8","4","16.79",
            "54","103.1","3.8","16.32","104.5","4","16.95",
            "55","103.7","3.8","16.47","105.1","4","17.1",
            "56","104.3","3.9","16.62","105.7","4.1","17.25",
            "57","105.3","3.9","16.77","106.2","4.1","17.4",
            "58","105.8","3.9","16.92","106.8","4.2","17.55",
            "59","105.8","4","17.07","107.2","4.2","17.71",
            "60","106.3","4","17.22","107.7","4.2","17.87",
            "66","109.3","4.1","18.06","110.8","4.4","18.90",
            "72","112.2","4.2","19.02","113.80","4.5","19.87",
            "78","115.2","4.4","20.11","116.80","4.7","21.03",
            "84","118.2","4.6","21.26","119.7","4.9","22.23",
            "90","121.1","4.7","22.48","122.5","5","23.44",
            "96","123.9","4.8","23.83","125.3","5.1","24.73",
            "102","126.7","4.9","25.24","128","5.2","26.09",
            "108","129.4","5","26.66","130.6","5.3","27.47",
            "114","132.1","5.1","28.10","133.1","5.3","28.84",
            "120","134.7","5.3","29.62","135.6","5.3","30.34",
            "126","137.7","5.4","31.44","138.1","5.5","31.81",
            "132","140.8","5.7","33.41","140.6","5.7","33.34",
            "138","144.2","5.9","35.53","143.2","5.9","35.18",
            "144","147.7","6.2","37.91","145.9","6.2","37.02",
            "150","151.2","6.1","40.87","149.2","6.5","39.48",
            "156","154.3","6","43.53","152.5","7.1","42.02",
            "162","156.7","5.8","45.68","156.3","7.8","45.2",
            "168","158.5","6.7","47.66","160","7.9","48.2",
            "174","159.9","5.6","49.4","163.3","8","51.1",
            "180","160.9","5.5","50.6","166.4","7.5","54",
            "186","161.5","5.5","51.6","169.1","6.8","56.4",
            "192","162","5.5","52.1","171","6.2","58.4",
            "198","162.4","5.5","52.6","172.4","6","60",
            "204","162.6","5.5","53","173.3","5.9","61.2");

    List<String> listPercentileIMC  = Arrays.asList(

                "1","24","-1.982373595","16.54777487","0.080127429",
                "1","25","-1.924100169","16.49442763","0.079233994",
                "1","26","-1.86549793","16.44259552","0.078389356",
                "1","27","-1.807261899","16.3922434","0.077593501",
                "1","28","-1.750118905","16.34333654","0.076846462",
                "1","29","-1.69481584","16.29584097","0.076148308",
                "1","30","-1.642106779","16.24972371","0.075499126",
                "1","31","-1.592744414","16.20495268","0.074898994",
                "1","32","-1.547442391","16.16149871","0.074347997",
                "1","33","-1.506902601","16.11933258","0.073846139",
                "1","34","-1.471770047","16.07842758","0.07339337",
                "1","35","-1.442628957","16.03875896","0.072989551",
                "1","36","-1.419991255","16.00030401","0.072634432",
                "1","37","-1.404277619","15.96304277","0.072327649",
                "1","38","-1.39586317","15.92695418","0.07206864",
                "1","39","-1.394935252","15.89202582","0.071856805",
                "1","40","-1.401671596","15.85824093","0.071691278",
                "1","41","-1.416100312","15.82558822","0.071571093",
                "1","42","-1.438164899","15.79405728","0.071495113",
                "1","43","-1.467669032","15.76364255","0.071462106",
                "1","44","-1.504376347","15.73433668","0.071470646",
                "1","45","-1.547942838","15.70613566","0.071519218",
                "1","46","-1.597896397","15.67904062","0.071606277",
                "1","47","-1.653732283","15.65305192","0.071730167",
                "1","48","-1.714869347","15.62817269","0.071889214",
                "1","49","-1.780673181","15.604408","0.072081737",
                "1","50","-1.850468473","15.58176458","0.072306081",
                "1","51","-1.923551865","15.56025067","0.072560637",
                "1","52","-1.999220429","15.5398746","0.07284384",
                "1","53","-2.076707178","15.52064993","0.073154324",
                "1","54","-2.155348017","15.50258427","0.073490667",
                "1","55","-2.234438552","15.48568973","0.073851672",
                "1","56","-2.313321723","15.46997718","0.074236235",
                "1","57","-2.391381273","15.45545692","0.074643374",
                "1","58","-2.468032491","15.44213961","0.075072264",
                "1","59","-2.542781541","15.43003207","0.075522104",
                "1","60","-2.61516595","15.41914163","0.07599225",
                "1","61","-2.684789516","15.40947356","0.076482128",
                "1","62","-2.751316949","15.40103139","0.076991232",
                "1","63","-2.81445945","15.39381785","0.077519149",
                "1","64","-2.87402476","15.38783094","0.07806539",
                "1","65","-2.92984048","15.38306945","0.078629592",
                "1","66","-2.981796828","15.37952958","0.079211369",
                "1","67","-3.029831343","15.37720582","0.079810334",
                "1","68","-3.073924224","15.37609107","0.080426086",
                "1","69","-3.114093476","15.37617677","0.081058206",
                "1","70","-3.15039004","15.37745304","0.081706249",
                "1","71","-3.182893018","15.37990886","0.082369741",
                "1","72","-3.21170511","15.38353217","0.083048178",
                "1","73","-3.23694834","15.38831005","0.083741021",
                "1","74","-3.25876011","15.39422883","0.0844477",
                "1","75","-3.277281546","15.40127496","0.085167651",
                "1","76","-3.292683774","15.40943252","0.085900184",
                "1","77","-3.305124073","15.41868691","0.086644667",
                "1","78","-3.314768951","15.42902273","0.087400421",
                "1","79","-3.321785992","15.44042439","0.088166744",
                "1","80","-3.326345795","15.45287581","0.088942897",
                "1","81","-3.328602731","15.46636218","0.089728202",
                "1","82","-3.328725277","15.48086704","0.090521875",
                "1","83","-3.32687018","15.49637465","0.091323162",
                "1","84","-3.323188896","15.51286936","0.092131305",
                "1","85","-3.317827016","15.53033563","0.092945544",
                "1","86","-3.310923871","15.54875807","0.093765118",
                "1","87","-3.302612272","15.56812143","0.09458927",
                "1","88","-3.293018361","15.58841065","0.095417247",
                "1","89","-3.282260813","15.60961101","0.096248301",
                "1","90","-3.270454609","15.63170735","0.097081694",
                "1","91","-3.257703616","15.65468563","0.097916698",
                "1","92","-3.244108214","15.67853139","0.098752593",
                "1","93","-3.229761713","15.70323052","0.099588675",
                "1","94","-3.214751287","15.72876911","0.100424251",
                "1","95","-3.199158184","15.75513347","0.101258643",
                "1","96","-3.18305795","15.78231007","0.102091189",
                "1","97","-3.166520664","15.8102856","0.102921245",
                "1","98","-3.1496103","15.83904708","0.103748189",
                "1","99","-3.132389637","15.86858123","0.104571386",
                "1","100","-3.114911153","15.89887562","0.105390269",
                "1","101","-3.097226399","15.92991765","0.106204258",
                "1","102","-3.079383079","15.96169481","0.107012788",
                "1","103","-3.061423765","15.99419489","0.107815327",
                "1","104","-3.043386071","16.02740607","0.108611374",
                "1","105","-3.025310003","16.0613159","0.109400388",
                "1","106","-3.007225737","16.09591292","0.110181915",
                "1","107","-2.989164598","16.13118532","0.110955478",
                "1","108","-2.971148225","16.16712234","0.111720691",
                "1","109","-2.953208047","16.20371168","0.112477059",
                "1","110","-2.935363951","16.24094239","0.1132242",
                "1","111","-2.917635157","16.27880346","0.113961734",
                "1","112","-2.900039803","16.31728385","0.114689291",
                "1","113","-2.882593796","16.35637267","0.115406523",
                "1","114","-2.865311266","16.39605916","0.116113097",
                "1","115","-2.848204697","16.43633265","0.116808702",
                "1","116","-2.831285052","16.47718256","0.117493042",
                "1","117","-2.81456189","16.51859843","0.11816584",
                "1","118","-2.79804347","16.56056987","0.118826835",
                "1","119","-2.781736856","16.60308661","0.119475785",
                "1","120","-2.765648008","16.64613844","0.120112464",
                "1","121","-2.749782197","16.68971518","0.120736656",
                "1","122","-2.734142443","16.73380695","0.121348181",
                "1","123","-2.718732873","16.77840363","0.121946849",
                "1","124","-2.703555506","16.82349538","0.122532501",
                "1","125","-2.688611957","16.86907238","0.123104991",
                "1","126","-2.673903164","16.91512487","0.123664186",
                "1","127","-2.659429443","16.96164317","0.124209969",
                "1","128","-2.645190534","17.00861766","0.124742239",
                "1","129","-2.631185649","17.05603879","0.125260905",
                "1","130","-2.617413511","17.10389705","0.125765895",
                "1","131","-2.603872392","17.15218302","0.126257147",
                "1","132","-2.590560148","17.20088732","0.126734613",
                "1","133","-2.577474253","17.25000062","0.12719826",
                "1","134","-2.564611831","17.29951367","0.127648067",
                "1","135","-2.551969684","17.34941726","0.128084023",
                "1","136","-2.539539972","17.39970308","0.128506192",
                "1","137","-2.527325681","17.45036072","0.128914497",
                "1","138","-2.515320235","17.50138161","0.129309001",
                "1","139","-2.503519447","17.55275674","0.129689741",
                "1","140","-2.491918934","17.60447714","0.130056765",
                "1","141","-2.480514136","17.6565339","0.130410133",
                "1","142","-2.469300331","17.70891811","0.130749913",
                "1","143","-2.458272656","17.76162094","0.131076187",
                "1","144","-2.447426113","17.81463359","0.131389042",
                "1","145","-2.436755595","17.86794729","0.131688579",
                "1","146","-2.426255887","17.92155332","0.131974905",
                "1","147","-2.415921689","17.97544299","0.132248138",
                "1","148","-2.405747619","18.02960765","0.132508403",
                "1","149","-2.395728233","18.08403868","0.132755834",
                "1","150","-2.385858029","18.1387275","0.132990575",
                "1","151","-2.376131459","18.19366555","0.133212776",
                "1","152","-2.366542942","18.24884431","0.133422595",
                "1","153","-2.357086871","18.3042553","0.133620197",
                "1","154","-2.347757625","18.35989003","0.133805756",
                "1","155","-2.338549576","18.41574009","0.133979452",
                "1","156","-2.3294571","18.47179706","0.13414147",
                "1","157","-2.320474586","18.52805255","0.134292005",
                "1","158","-2.311596446","18.5844982","0.134431256",
                "1","159","-2.302817124","18.64112567","0.134559427",
                "1","160","-2.294131107","18.69792663","0.134676731",
                "1","161","-2.285532933","18.75489278","0.134783385",
                "1","162","-2.277017201","18.81201584","0.134879611",
                "1","163","-2.268578584","18.86928753","0.134965637",
                "1","164","-2.260211837","18.92669959","0.135041695",
                "1","165","-2.251911809","18.98424378","0.135108024",
                "1","166","-2.243673453","19.04191185","0.135164867",
                "1","167","-2.235491842","19.09969557","0.135212469",
                "1","168","-2.227362173","19.15758672","0.135251083",
                "1","169","-2.21927979","19.21557707","0.135280963",
                "1","170","-2.211240187","19.27365839","0.135302371",
                "1","171","-2.203239029","19.33182247","0.135315568",
                "1","172","-2.195272161","19.39006106","0.135320824",
                "1","173","-2.187335625","19.44836594","0.135318407",
                "1","174","-2.179425674","19.50672885","0.135308594",
                "1","175","-2.171538789","19.56514153","0.135291662",
                "1","176","-2.163671689","19.62359571","0.135267891",
                "1","177","-2.155821357","19.6820831","0.135237567",
                "1","178","-2.147985046","19.74059538","0.135200976",
                "1","179","-2.140160305","19.7991242","0.135158409",
                "1","180","-2.132344989","19.85766121","0.135110159",
                "1","181","-2.124537282","19.916198","0.135056522",
                "1","182","-2.116735712","19.97472615","0.134997797",
                "1","183","-2.108939167","20.03323719","0.134934285",
                "1","184","-2.10114692","20.09172262","0.134866291",
                "1","185","-2.093358637","20.15017387","0.134794121",
                "1","186","-2.085574403","20.20858236","0.134718085",
                "1","187","-2.077794735","20.26693944","0.134638494",
                "1","188","-2.070020599","20.32523642","0.134555663",
                "1","189","-2.062253431","20.38346455","0.13446991",
                "1","190","-2.054495145","20.44161501","0.134381553",
                "1","191","-2.046748156","20.49967894","0.134290916",
                "1","192","-2.039015385","20.5576474","0.134198323",
                "1","193","-2.031300282","20.6155114","0.134104101",
                "1","194","-2.023606828","20.67326189","0.134008581",
                "1","195","-2.015942013","20.73088905","0.133912066",
                "1","196","-2.008305745","20.7883851","0.133814954",
                "1","197","-2.000706389","20.84574003","0.133717552",
                "1","198","-1.993150137","20.90294449","0.1336202",
                "1","199","-1.985643741","20.95998909","0.133523244",
                "1","200","-1.97819451","21.01686433","0.133427032",
                "1","201","-1.970810308","21.07356067","0.133331914",
                "1","202","-1.96349954","21.1300685","0.133238245",
                "1","203","-1.956271141","21.18637813","0.133146383",
                "1","204","-1.949134561","21.24247982","0.13305669",
                "1","205","-1.942099744","21.29836376","0.132969531",
                "1","206","-1.935177101","21.35402009","0.132885274",
                "1","207","-1.92837748","21.40943891","0.132804292",
                "1","208","-1.921712136","21.46461026","0.132726962",
                "1","209","-1.915192685","21.51952414","0.132653664",
                "1","210","-1.908831065","21.57417053","0.132584784",
                "1","211","-1.902639482","21.62853937","0.132520711",
                "1","212","-1.896630358","21.68262062","0.132461838",
                "1","213","-1.890816268","21.73640419","0.132408563",
                "1","214","-1.885209876","21.78988003","0.132361289",
                "1","215","-1.879823505","21.84303819","0.132320427",
                "1","216","-1.874670324","21.8958685","0.132286382",
                "1","217","-1.869760299","21.94836168","0.1322596",
                "1","218","-1.865113245","22.00050569","0.132240418",
                "1","219","-1.860734944","22.05229242","0.13222933",
                "1","220","-1.85663384","22.10371305","0.132226801",
                "1","221","-1.852827186","22.15475603","0.132233201",
                "1","222","-1.849323204","22.20541249","0.132248993",
                "1","223","-1.846131607","22.255673","0.132274625",
                "1","224","-1.843261294","22.30552831","0.132310549",
                "1","225","-1.840720248","22.3549693","0.132357221",
                "1","226","-1.83851544","22.40398706","0.132415103",
                "1","227","-1.83665586","22.45257182","0.132484631",
                "1","228","-1.835138046","22.50071778","0.132566359",
                "1","229","-1.833972004","22.54841437","0.132660699",
                "1","230","-1.833157751","22.59565422","0.132768153",
                "1","231","-1.83269562","22.64242956","0.132889211",
                "1","232","-1.832584342","22.68873292","0.133024368",
                "1","233","-1.832820974","22.73455713","0.133174129",
                "1","234","-1.833400825","22.7798953","0.133338999",
                "1","235","-1.834317405","22.82474087","0.133519496",
                "1","236","-1.83555752","22.86908912","0.133716192",
                "1","237","-1.837119466","22.91293151","0.133929525",
                "1","238","-1.838987063","22.95626373","0.134160073",
                "1","239","-1.841146139","22.99908062","0.134408381",
                "1","240","-1.84233016","23.02029424","0.134539365",
                "2","24","-1.024496827","16.38804056","0.085025838",
                "2","25","-1.102698353","16.3189719","0.084214052",
                "2","26","-1.18396635","16.25207985","0.083455124",
                "2","27","-1.268071036","16.18734669","0.082748284",
                "2","28","-1.354751525","16.12475448","0.082092737",
                "2","29","-1.443689692","16.06428762","0.081487717",
                "2","30","-1.53454192","16.00593001","0.080932448",
                "2","31","-1.626928093","15.94966631","0.080426175",
                "2","32","-1.720434829","15.89548197","0.079968176",
                "2","33","-1.814635262","15.84336179","0.079557735",
                "2","34","-1.909076262","15.79329146","0.079194187",
                "2","35","-2.003296102","15.7452564","0.078876895",
                "2","36","-2.096828937","15.69924188","0.078605255",
                "2","37","-2.189211877","15.65523282","0.078378696",
                "2","38","-2.279991982","15.61321371","0.078196674",
                "2","39","-2.368732949","15.57316843","0.078058667",
                "2","40","-2.455021314","15.53508019","0.077964169",
                "2","41","-2.538471972","15.49893145","0.077912684",
                "2","42","-2.618732901","15.46470384","0.077903716",
                "2","43","-2.695488973","15.43237817","0.077936763",
                "2","44","-2.768464816","15.40193436","0.078011309",
                "2","45","-2.837426693","15.37335154","0.078126817",
                "2","46","-2.902178205","15.34660842","0.078282739",
                "2","47","-2.962580386","15.32168181","0.078478449",
                "2","48","-3.018521987","15.29854897","0.078713325",
                "2","49","-3.069936555","15.27718618","0.078986694",
                "2","50","-3.116795864","15.2575692","0.079297841",
                "2","51","-3.159107331","15.23967338","0.079646006",
                "2","52","-3.196911083","15.22347371","0.080030389",
                "2","53","-3.230276759","15.20894491","0.080450145",
                "2","54","-3.259300182","15.19606152","0.080904391",
                "2","55","-3.284099963","15.18479799","0.081392203",
                "2","56","-3.30481415","15.17512871","0.081912623",
                "2","57","-3.321596954","15.16702811","0.082464661",
                "2","58","-3.334615646","15.16047068","0.083047295",
                "2","59","-3.344047622","15.15543107","0.083659478",
                "2","60","-3.35007771","15.15188405","0.084300139",
                "2","61","-3.352893805","15.14980479","0.0849682",
                "2","62","-3.352691376","15.14916825","0.085662539",
                "2","63","-3.34966438","15.14994984","0.086382035",
                "2","64","-3.343998803","15.15212585","0.087125591",
                "2","65","-3.335889574","15.15567186","0.087892047",
                "2","66","-3.325522491","15.16056419","0.088680264",
                "2","67","-3.31307846","15.16677947","0.089489106",
                "2","68","-3.298732648","15.17429464","0.090317434",
                "2","69","-3.282653831","15.18308694","0.091164117",
                "2","70","-3.265003896","15.1931339","0.092028028",
                "2","71","-3.245937506","15.20441335","0.092908048",
                "2","72","-3.225606516","15.21690296","0.093803033",
                "2","73","-3.204146115","15.2305815","0.094711916",
                "2","74","-3.181690237","15.24542745","0.095633595",
                "2","75","-3.158363475","15.26141966","0.096566992",
                "2","76","-3.134282833","15.27853728","0.097511046",
                "2","77","-3.109557879","15.29675967","0.09846471",
                "2","78","-3.084290931","15.31606644","0.099426955",
                "2","79","-3.058577292","15.33643745","0.100396769",
                "2","80","-3.032505499","15.35785274","0.101373159",
                "2","81","-3.0061576","15.38029261","0.10235515",
                "2","82","-2.979609448","15.40373754","0.103341788",
                "2","83","-2.952930993","15.42816819","0.104332139",
                "2","84","-2.926186592","15.45356545","0.105325289",
                "2","85","-2.899435307","15.47991037","0.106320346",
                "2","86","-2.872731211","15.50718419","0.10731644",
                "2","87","-2.846123683","15.53536829","0.108312721",
                "2","88","-2.819657704","15.56444426","0.109308364",
                "2","89","-2.793374145","15.5943938","0.110302563",
                "2","90","-2.767310047","15.6251988","0.111294537",
                "2","91","-2.741498897","15.65684126","0.112283526",
                "2","92","-2.715970894","15.68930333","0.113268793",
                "2","93","-2.690753197","15.7225673","0.114249622",
                "2","94","-2.665870146","15.75661555","0.115225321",
                "2","95","-2.641343436","15.79143062","0.116195218",
                "2","96","-2.617192204","15.82699517","0.117158667",
                "2","97","-2.593430614","15.86329241","0.118115073",
                "2","98","-2.570076037","15.90030484","0.119063807",
                "2","99","-2.547141473","15.93801545","0.12000429",
                "2","100","-2.524635245","15.97640787","0.120935994",
                "2","101","-2.502569666","16.01546483","0.121858355",
                "2","102","-2.48095189","16.05516984","0.12277087",
                "2","103","-2.459785573","16.09550688","0.123673085",
                "2","104","-2.439080117","16.13645881","0.124564484",
                "2","105","-2.418838304","16.17800955","0.125444639",
                "2","106","-2.399063683","16.22014281","0.126313121",
                "2","107","-2.379756861","16.26284277","0.127169545",
                "2","108","-2.360920527","16.30609316","0.128013515",
                "2","109","-2.342557728","16.34987759","0.128844639",
                "2","110","-2.324663326","16.39418118","0.129662637",
                "2","111","-2.307240716","16.43898741","0.130467138",
                "2","112","-2.290287663","16.48428082","0.131257852",
                "2","113","-2.273803847","16.53004554","0.132034479",
                "2","114","-2.257782149","16.57626713","0.132796819",
                "2","115","-2.242227723","16.62292864","0.133544525",
                "2","116","-2.227132805","16.67001572","0.134277436",
                "2","117","-2.212495585","16.71751288","0.134995324",
                "2","118","-2.19831275","16.76540496","0.135697996",
                "2","119","-2.184580762","16.81367689","0.136385276",
                "2","120","-2.171295888","16.86231366","0.137057004",
                "2","121","-2.158454232","16.91130036","0.137713039",
                "2","122","-2.146051754","16.96062216","0.138353254",
                "2","123","-2.134084303","17.0102643","0.138977537",
                "2","124","-2.122547629","17.06021213","0.139585795",
                "2","125","-2.111437411","17.11045106","0.140177947",
                "2","126","-2.100749266","17.16096656","0.140753927",
                "2","127","-2.090478774","17.21174424","0.141313686",
                "2","128","-2.080621484","17.26276973","0.141857186",
                "2","129","-2.071172932","17.31402878","0.142384404",
                "2","130","-2.062128649","17.3655072","0.142895332",
                "2","131","-2.053484173","17.4171909","0.143389972",
                "2","132","-2.045235058","17.46906585","0.143868341",
                "2","133","-2.03737688","17.52111811","0.144330469",
                "2","134","-2.029906684","17.57333347","0.144776372",
                "2","135","-2.022817914","17.62569869","0.145206138",
                "2","136","-2.016107084","17.67819987","0.145619819",
                "2","137","-2.009769905","17.7308234","0.146017491",
                "2","138","-2.003802134","17.78355575","0.146399239",
                "2","139","-1.998199572","17.83638347","0.146765161",
                "2","140","-1.992958064","17.88929321","0.147115364",
                "2","141","-1.988073505","17.94227168","0.147449967",
                "2","142","-1.983541835","17.9953057","0.147769097",
                "2","143","-1.979359041","18.04838216","0.148072891",
                "2","144","-1.975521156","18.10148804","0.148361495",
                "2","145","-1.972024258","18.15461039","0.148635067",
                "2","146","-1.968864465","18.20773639","0.148893769",
                "2","147","-1.966037938","18.26085325","0.149137776",
                "2","148","-1.963540872","18.31394832","0.14936727",
                "2","149","-1.961369499","18.36700902","0.149582439",
                "2","150","-1.959520079","18.42002284","0.149783482",
                "2","151","-1.9579889","18.47297739","0.149970604",
                "2","152","-1.956772271","18.52586035","0.15014402",
                "2","153","-1.95586652","18.57865951","0.15030395",
                "2","154","-1.955267984","18.63136275","0.150450621",
                "2","155","-1.954973011","18.68395801","0.15058427",
                "2","156","-1.954977947","18.73643338","0.150705138",
                "2","157","-1.955279136","18.788777","0.150813475",
                "2","158","-1.955872909","18.84097713","0.150909535",
                "2","159","-1.956755579","18.89302212","0.150993582",
                "2","160","-1.957923436","18.94490041","0.151065883",
                "2","161","-1.959372737","18.99660055","0.151126714",
                "2","162","-1.9610997","19.04811118","0.151176355",
                "2","163","-1.963100496","19.09942105","0.151215094",
                "2","164","-1.96537124","19.15051899","0.151243223",
                "2","165","-1.967907983","19.20139397","0.151261042",
                "2","166","-1.970706706","19.25203503","0.151268855",
                "2","167","-1.973763307","19.30243131","0.151266974",
                "2","168","-1.977073595","19.35257209","0.151255713",
                "2","169","-1.980633277","19.40244671","0.151235395",
                "2","170","-1.984437954","19.45204465","0.151206347",
                "2","171","-1.988483106","19.50135548","0.151168902",
                "2","172","-1.992764085","19.55036888","0.151123398",
                "2","173","-1.997276103","19.59907464","0.15107018",
                "2","174","-2.002014224","19.64746266","0.151009595",
                "2","175","-2.00697335","19.69552294","0.150942",
                "2","176","-2.012148213","19.7432456","0.150867753",
                "2","177","-2.017533363","19.79062086","0.150787221",
                "2","178","-2.023123159","19.83763907","0.150700774",
                "2","179","-2.028911755","19.88429066","0.150608788",
                "2","180","-2.034893091","19.9305662","0.150511645",
                "2","181","-2.041060881","19.97645636","0.150409731",
                "2","182","-2.047408604","20.02195192","0.15030344",
                "2","183","-2.05392949","20.06704377","0.150193169",
                "2","184","-2.060616513","20.11172291","0.150079322",
                "2","185","-2.067462375","20.15598047","0.149962308",
                "2","186","-2.074459502","20.19980767","0.14984254",
                "2","187","-2.081600029","20.24319586","0.149720441",
                "2","188","-2.088875793","20.28613648","0.149596434",
                "2","189","-2.096278323","20.32862109","0.149470953",
                "2","190","-2.103798828","20.37064138","0.149344433",
                "2","191","-2.111428194","20.41218911","0.149217319",
                "2","192","-2.119156972","20.45325617","0.14909006",
                "2","193","-2.126975375","20.49383457","0.14896311",
                "2","194","-2.134873266","20.5339164","0.148836931",
                "2","195","-2.142840157","20.57349387","0.148711989",
                "2","196","-2.150865204","20.61255929","0.148588757",
                "2","197","-2.158937201","20.65110506","0.148467715",
                "2","198","-2.167044578","20.6891237","0.148349348",
                "2","199","-2.175176987","20.72660728","0.14823412",
                "2","200","-2.183317362","20.76355011","0.148122614",
                "2","201","-2.191457792","20.79994337","0.148015249",
                "2","202","-2.199583649","20.83578051","0.147912564",
                "2","203","-2.207681525","20.87105449","0.147815078",
                "2","204","-2.215737645","20.90575839","0.147723315",
                "2","205","-2.223739902","20.93988477","0.147637768",
                "2","206","-2.231667995","20.97342858","0.147559083",
                "2","207","-2.239511942","21.00638171","0.147487716",
                "2","208","-2.247257081","21.0387374","0.14742421",
                "2","209","-2.254885145","21.07048996","0.147369174",
                "2","210","-2.26238209","21.10163241","0.147323144",
                "2","211","-2.269731517","21.13215845","0.147286698",
                "2","212","-2.276917229","21.16206171","0.147260415",
                "2","213","-2.283925442","21.1913351","0.147244828",
                "2","214","-2.290731442","21.21997472","0.147240683",
                "2","215","-2.29732427","21.24797262","0.147248467",
                "2","216","-2.303687802","21.27532239","0.14726877",
                "2","217","-2.309799971","21.30201933","0.147302299",
                "2","218","-2.315651874","21.32805489","0.147349514",
                "2","219","-2.32121731","21.35342563","0.147411215",
                "2","220","-2.326481911","21.37812462","0.147487979",
                "2","221","-2.331428139","21.40214589","0.147580453",
                "2","222","-2.336038473","21.42548351","0.147689289",
                "2","223","-2.34029545","21.44813156","0.14781515",
                "2","224","-2.344181703","21.47008412","0.147958706",
                "2","225","-2.34768","21.49133529","0.148120633",
                "2","226","-2.350773286","21.51187918","0.148301619",
                "2","227","-2.353444725","21.53170989","0.148502355",
                "2","228","-2.355677743","21.55082155","0.148723546",
                "2","229","-2.35745607","21.56920824","0.148965902",
                "2","230","-2.358763788","21.58686406","0.149230142",
                "2","231","-2.359585369","21.60378309","0.149516994",
                "2","232","-2.359905726","21.61995939","0.149827195",
                "2","233","-2.359710258","21.635387","0.150161492",
                "2","234","-2.358980464","21.65006126","0.150520734",
                "2","235","-2.357714508","21.6639727","0.150905439",
                "2","236","-2.355892424","21.67711736","0.151316531",
                "2","237","-2.353501353","21.68948935","0.151754808",
                "2","238","-2.350528726","21.70108288","0.152221086",
                "2","239","-2.346962247","21.71189225","0.152716206",
                "2","240","-2.34495843","21.71699934","0.152974718");

    List<String> listPercentilePC  = Arrays.asList(
              //  "1","0","4.310927464","37.19361054","0.047259148",

                "1","1","3.869576802","39.20742929","0.040947903",
                "1","2","3.305593039","40.65233195","0.037027722",
                "1","3","2.720590297","41.76516959","0.034364245",
                "1","4","2.16804824","42.66116148","0.032462175",
                "1","5","1.675465689","43.40488731","0.031064702",
                "1","6","1.255160322","44.03609923","0.03002267",
                "1","7","0.91054114","44.58096912","0.029242173",
                "1","8","0.639510474","45.05761215","0.028660454",
                "1","9","0.436978864","45.4790756","0.0282336",
                "1","10","0.296275856","45.85505706","0.027929764",
                "1","11","0.210107251","46.19295427","0.027725179",
                "1","12","0.171147024","46.49853438","0.027601686",
                "1","13","0.172393886","46.77637684","0.027545148",
                "1","14","0.207371541","47.03017599","0.027544382",
                "1","15","0.270226126","47.2629533","0.027590417",
                "1","16","0.355757274","47.47720989","0.02767598",
                "1","17","0.459407627","47.67503833","0.027795115",
                "1","18","0.577227615","47.85820606","0.0279429",
                "1","19","0.705826778","48.02821867","0.028115241",
                "1","20","0.842319055","48.18636864","0.028308707",
                "1","21","0.984266833","48.3337732","0.028520407",
                "1","22","1.129626698","48.47140432","0.028747896",
                "1","23","1.276691223","48.60011223","0.028989089",
                "1","24","1.424084853","48.72064621","0.029242207",
                "1","25","1.570621291","48.83366629","0.029505723",
                "1","26","1.715393998","48.93976089","0.029778323",
                "1","27","1.857652984","49.03945383","0.030058871",
                "1","28","1.996810563","49.13321432","0.030346384",
                "1","29","2.132411346","49.22146409","0.030640006",
                "1","30","2.264111009","49.30458348","0.030938992",
                "1","31","2.391658052","49.38291658","0.031242693",
                "1","32","2.514878222","49.45677569","0.031550537",
                "1","33","2.633661226","49.526445","0.031862026",
                "1","34","2.747949445","49.59218385","0.03217672",
                "1","35","2.857728375","49.65422952","0.032494231",
                "1","36","2.910932095","49.68393611","0.032653934",
                "2","0","-1.440271514","36.03453876","0.042999604",
                "2","1","-1.581016348","37.97671987","0.038067862",
                "2","2","-1.593136386","39.3801263","0.035079612",
                "2","3","-1.521492427","40.46773733","0.033096443",
                "2","4","-1.394565915","41.34841008","0.03170963",
                "2","5","-1.231713389","42.0833507","0.030709039",
                "2","6","-1.046582628","42.71033603","0.029974303",
                "2","7","-0.848932692","43.25428882","0.029430992",
                "2","8","-0.645779124","43.73249646","0.029030379",
                "2","9","-0.442165412","44.15742837","0.028739112",
                "2","10","-0.24163206","44.53836794","0.028533537",
                "2","11","-0.046673786","44.88240562","0.028396382",
                "2","12","0.141031094","45.19507651","0.028314722",
                "2","13","0.320403169","45.48078147","0.028278682",
                "2","14","0.490807133","45.74307527","0.028280585",
                "2","15","0.65193505","45.98486901","0.028314363",
                "2","16","0.803718086","46.20857558","0.028375159",
                "2","17","0.946259679","46.41621635","0.028459033",
                "2","18","1.079784984","46.60950084","0.028562759",
                "2","19","1.204602687","46.78988722","0.028683666",
                "2","20","1.321076285","46.95862881","0.028819525",
                "2","21","1.429602576","47.11681039","0.028968459",
                "2","22","1.530595677","47.26537682","0.029128879",
                "2","23","1.624475262","47.40515585","0.029299426",
                "2","24","1.71165803","47.53687649","0.029478937",
                "2","25","1.792551616","47.66118396","0.029666406",
                "2","26","1.867550375","47.77865186","0.02986096",
                "2","27","1.93703258","47.8897923","0.030061839",
                "2","28","2.001358669","47.99506422","0.030268375",
                "2","29","2.060870301","48.09488048","0.030479985",
                "2","30","2.115889982","48.18961365","0.03069615",
                "2","31","2.16672113","48.2796011","0.030916413",
                "2","32","2.21364844","48.36514917","0.031140368",
                "2","33","2.256943216","48.44653703","0.031367651",
                "2","34","2.296844024","48.52401894","0.031597939",
                "2","35","2.333589434","48.59782828","0.031830942",
                "2","36","2.350847202","48.63342328","0.031948378");
}

