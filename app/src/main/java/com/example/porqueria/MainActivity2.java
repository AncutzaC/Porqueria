package com.example.porqueria;

import static com.example.porqueria.Porqueria.addBirthWeight;
import static com.example.porqueria.Porqueria.addGender;
import static com.example.porqueria.Porqueria.addHeight;
import static com.example.porqueria.Porqueria.addHeightFather;
import static com.example.porqueria.Porqueria.addHeightMother;
import static com.example.porqueria.Porqueria.addMonths;
import static com.example.porqueria.Porqueria.addPerimeter;
import static com.example.porqueria.Porqueria.addTAD;
import static com.example.porqueria.Porqueria.addTAS;
import static com.example.porqueria.Porqueria.addTotalMonths;
import static com.example.porqueria.Porqueria.addWeight;
import static com.example.porqueria.Porqueria.addYears;
import static com.example.porqueria.Porqueria.greutateaCorespunzatoareTalieiPacientului;
import static com.example.porqueria.Porqueria.zscoreHeight;
import static com.example.porqueria.Porqueria.zscorePC;
import static com.example.porqueria.Porqueria.zscoreWeight;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.apache.commons.math3.distribution.NormalDistribution;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        double zscoreWeightF = getDoubleNumber(zscoreWeight);
        double zscoreHeightF = getDoubleNumber(zscoreHeight);
        double zscoreHeightRo = getDoubleNumber(Porqueria.zscoreHeightRo);
        double zscoreIMC = getDoubleNumber(Porqueria.zscoreIMC);
        double zscorePC1 = getDoubleNumber(zscorePC);
        double imc = getDoubleNumber(getIMC());


        double percentilaWeight = getDoubleNumber(zScoreToPercentile(zscoreWeightF));
        double percentilaHeight = getDoubleNumber(zScoreToPercentile(zscoreHeightF));
        double percentilaPC = getDoubleNumber(zScoreToPercentile(zscorePC));
        double procentWeightToHeightRo = getDoubleNumber(Porqueria.procentWeightToHeightRo);
        double greutateaCorespunzatoareTalieiPacientului = getDoubleNumber(Porqueria.greutateaCorespunzatoareTalieiPacientului);
        double percentilaIMC = getDoubleNumber(zScoreToPercentile(zscoreIMC));

        // afisam percentilele de TAS si TAD
        MainActivity2 mainActivity2 = new MainActivity2();
        MyResult result = mainActivity2.getPercentileTAGeneral(addTAS, 0);
        MyResult result1 = mainActivity2.getPercentileTAGeneral(addTAD, 8);


        //afisam rezultatele dorite (cele prelucrate)/*
        TextView resultOfLabour = (TextView) findViewById(R.id.resultOfLabour);


        resultOfLabour.setText(

                "Sex: " + addGender + "\n" +
                        "Varsta: " + addYears + " ani, " + addMonths + " luni;\n\n" +
                        "_____________________________________________\n"
                        + "Talia: " + addHeight + "cm\n" +
                        "* percentila: " + percentilaHeight + " pt varsta si sex (cf CDC, WHO);\n" +
                        "* " + zscoreHeightF + " DS pt varsta si sex (cf CDC, WHO);\n" +
                        "* " + zscoreHeightRo + "DS pt varsta si sex (cf standardelor franceze '97)\n\n" +
                        "* Talie mama = " + addHeightMother + " cm, Talie tata = " + addHeightFather + " cm,\nTalia tinta = " + getTaliaTinta() + " cm\n" +
                        "_____________________________________________\n" +
                        "Greutatea: " + addWeight + "kg\n" +
                        "* percentila: " + percentilaWeight + " pt varsta si sex (cf CDC, WHO);\n"
                        + "* " + zscoreWeight + " DS pt varsta si sex (cf CDC, WHO)\n" +
                        "* " + procentWeightToHeightRo + " % " + getAnswer(procentWeightToHeightRo) + ", cf std franceze '97; -->> " +
                        "Greutatea corespunzatoare taliei pacientului este: " + greutateaCorespunzatoareTalieiPacientului + " kg\n" +
                        "* S.c. = " + getDoubleNumber(getSuprafataCorporala()) + "mp;\n" +
                        "_____________________________________________\n" +
                        "IMC = " + imc + "kg/m2, zscore = " + zscoreIMC + ", percentila " + percentilaIMC + ";\n" +
                        "Indice nutritional = " + getDoubleNumber(getIndiceNutritional()) + getAnswerIN() + "\n" +
                        "Indice ponderal = " + getDoubleNumber(getIndicePonderal()) + getAnswerIP() + "\n" +
                        "_____________________________________________\n" +
                        "TA = " + Porqueria.addTAS + "/ " + addTAD + " mmHg;\n" +
                        "* p TAS coresp varstei si sexului este " + result.getFirst() + "; \n" +
                        "* p TAD coresp varstei si sexului este " + result1.getFirst() + "; \n" +
                        "-> p50= " + result.getSecond() + "/ " + result1.getSecond() + "; \n" +
                        "-> p90= " + result.getThird() + "/ " + result1.getThird() + "; \n" +
                        "-> p95= " + result.getForth() + "/ " + result1.getForth() + "; \n" +
                        "-> p99= " + result.getFifth() + "/ " + result1.getFifth() + "." + ";\n" +
                        "_____________________________________________\n" +
                        "PC = " + addPerimeter + " cm, " + getDoubleNumber(zscorePC) + " DS, p" + getDoubleNumber(percentilaPC) + ";\n" +
                        "_____________________________________________\n" +
                        "\n\n\n\n\n\n" +
                        "Conform CDC pt varsta sub 2 ani si conform WHO pt 2-20 ani.");


    }

    public static double getDoubleNumber(double desiredNumber) {
        double bigDecimal2 = 0;
        if (desiredNumber == 0 || Double.isInfinite(desiredNumber) || Double.isNaN(desiredNumber)) {
            bigDecimal2 = 0;
            return bigDecimal2;
        }
        if (desiredNumber != 0) {
            BigDecimal bigDecimal = new BigDecimal(desiredNumber);
            bigDecimal2 = bigDecimal.setScale(2, RoundingMode.HALF_UP).doubleValue();
        }
        return bigDecimal2;
    }

    public static double zScoreToPercentile(double zScore) {
        double number = 0;
        if (zScore == 0) {
            return number;
        }
        if (zScore != 0) {
            NormalDistribution dist = new NormalDistribution();
            number = dist.cumulativeProbability(zScore) * 100;
        }
        return number;
    }

    public static String getAnswer(Double procent) {
        String raspuns = " ";
        if (procent <= -10) {
            raspuns = "deficit ponderal pentru talie si sex";
        }
        if (procent >= 10 && procent <= 20) {
            raspuns = "exces ponderal pentru talie si sex, adica suprapondere";
        }
        if (procent > 20) {
            raspuns = "exces ponderal pentru talie si sex, adica obezitate";
        }
        if (procent > 0 && procent < 10) {
            raspuns = "kg peste greutatea corespunzatoare taliei si sexul pacientului, insa incadrabil ca normoponderal";
        }
        if (procent < 0 && procent > -10) {
            raspuns = "kg sub greutatea corespunzatoare taliei si sexul pacientului, insa incadrabil ca normoponderal";
        }
        if (procent == 0) {
            raspuns = "greutate corespunzatoare taliei si sexului pacientului";
        }
        return raspuns;
    }

    public double getIMC() {
        if (addTotalMonths > 23) {
            return (addWeight * 10000) / (addHeight * addHeight);
        } else return 0;
    }

    public double getGreutateaIdeala() {
        int i = 0;
        double greutateaIdeala = 0;
        if (addTotalMonths * 30 < 24 * 30) {

            while (i < 31 && i <= addTotalMonths * 30) {
                greutateaIdeala = greutateaIdeala + (double) (500 / 30);
                i = i + 1;
            }
            while (i > 30 && i < 121 && i <= addTotalMonths * 30) {
                greutateaIdeala = greutateaIdeala + (double) (750 / 30);
                i = i + 1;
            }
            while (i > 120 && i < 241 && i <= addTotalMonths * 30) {
                greutateaIdeala = greutateaIdeala + (double) (500 / 30);
                i = i + 1;
            }
            while (i > 240 && i < 720 && i <= addTotalMonths * 30) {
                greutateaIdeala = greutateaIdeala + (double) (250 / 30);
                i = i + 1;
            }
        }
        return greutateaIdeala;
    }

    public double getIndicePonderal() {
        if (addTotalMonths < 24) {
            return addWeight * 1000 / (addBirthWeight + getGreutateaIdeala());
        } else return 0;
    }

    public double getIndiceNutritional() {
        if (addTotalMonths < 24) {
            return addWeight / (greutateaCorespunzatoareTalieiPacientului);
        } else return 0;
    }

    public String getAnswerIP() {
        if (addTotalMonths < 24) {
            String raspuns = " ";
            if (getIndicePonderal() > 1.1) {
                raspuns = ", copil paratrofic;";
            }
            if (getIndicePonderal() > 0.89 && getIndicePonderal() <= 1.1) {
                raspuns = ", copil eutrofic;";
            }
            if (getIndicePonderal() > 0.76 && getIndicePonderal() <= 0.89) {
                raspuns = ", malnutritie proteino-calorica grad I;";
            }
            if (getIndicePonderal() > 0.60 && getIndicePonderal() <= 0.75) {
                raspuns = ", malnutritie proteino-calorica grad II;";
            }
            if (getIndicePonderal() <= 0.60 && getIndicePonderal() != 0) {
                raspuns = ", malnutritie proteino-calorica grad III;";
            }
            if (getIndicePonderal() == 0) {
                raspuns = "";
            }
            return raspuns;
        }
        return "N/A";
    }

    public String getAnswerIN() {
        if (addTotalMonths < 24) {
            String raspuns = " ";
            if (getIndiceNutritional() > 1.1) {
                raspuns = ", copil paratrofic;";
            }
            if (getIndiceNutritional() > 0.89 && getIndiceNutritional() <= 1.1) {
                raspuns = ", copil eutrofic;";
            }
            if (getIndiceNutritional() > 0.80 && getIndiceNutritional() <= 0.89) {
                raspuns = ", malnutritie proteino-calorica grad I;";
            }
            if (getIndiceNutritional() > 0.70 && getIndiceNutritional() <= 0.80) {
                raspuns = ", malnutritie proteino-calorica grad II;";
            }
            if (getIndiceNutritional() <= 0.70 && getIndiceNutritional() != 0) {
                raspuns = ", malnutritie proteino-calorica grad III;";
            }
            if (getIndiceNutritional() == 0) {
                raspuns = "";
            }
            return raspuns;
        }
        return "N/A";
    }

    public double getSuprafataCorporala() {
        if (addWeight == 0) {
            return 0;
        } else return (addWeight * 4 + 7) / (addWeight + 90);
    }

    public double getTaliaTinta() {
        double taliaTinta = 0;
        if (addGender.equals("Feminin")) {
            taliaTinta = (addHeightMother + addHeightFather - 13) / 2;
        }
        if (addGender.equals("Masculin")) {
            taliaTinta = (addHeightMother + addHeightFather + 13) / 2;
        }
        if (addHeightMother == 0 || addHeightFather == 0) {
            taliaTinta = 0;
        }
        return getDoubleNumber(taliaTinta);
    }

    static final class MyResult {
        private final int second, third, forth, fifth;
        private final String first;

        public MyResult(String first, int second, int third, int forth, int fifth) {
            this.first = first;
            this.second = second;
            this.third = third;
            this.forth = forth;
            this.fifth = fifth;
        }

        public String getFirst() {
            return String.valueOf(first);
        }

        public int getSecond() {
            return second;
        }

        public int getThird() {
            return third;
        }

        public int getForth() {
            return forth;
        }

        public int getFifth() {
            return fifth;
        }
    }

    // get percentile of height and return the percentile interval in which we can find it
    // interval of percentiles: 0, 5, 10, 25, 50, 75, 90, 95, 100
    public int getIntervalPercentileForHeight() {
        boolean b = true;
        int pH1 = 0;
        List<Integer> listPercentileHeight = Arrays.asList(0, 5, 10, 25, 50, 75, 90, 95, 100);
        for (int k = 0; k < listPercentileHeight.size(); k++) {
            if (getDoubleNumber(zScoreToPercentile((zscoreHeight))) <= listPercentileHeight.get(k) && b) {
                pH1 = k - 1;
                b = false;
            }
        }
        return pH1;
    }

    // gets the apropriate list in fonction to the gender
    private ArrayList getList() {
        ArrayList<String> list = new ArrayList<String>();
        if (addGender.equals("Feminin")) {
            list.addAll(Porqueria.listPercentileTAF);
        } else list.addAll(Porqueria.listPercentileTAM);
        return list;
    }

    // get the percentile TAS for a given TA (for Feminine or Masculine)
    // value - if 0 then outputs TAS values, if 8 then outputs TAD values
    public MyResult getPercentileTAGeneral(int addTA, int value) {
        int percentila50 = 0, percentila90 = 0, percentila95 = 0, percentila99 = 0;
        double percentilaTAmax, percentilaTAmin;
        String percentilaTA = null;
        boolean c = true;
        int pH1 = getIntervalPercentileForHeight() + 1 + value;
        //if no TA is introduced, returns 0
        if (addTA == 0) return new MyResult("0", 0, 0, 0, 0);
        //getting the list
        ArrayList<String> list = new ArrayList<String>();
        list.addAll(getList());
        //finding the percentile values
        for (int i = 0; i < list.size(); i += 61) {
            if (Integer.parseInt(list.get(i)) == addYears) {
                for (int j = i + 1 + pH1; j < i + 61; j += 15) {
                    percentila50 = Integer.parseInt(list.get(i + pH1 + 1));
                    percentila90 = Integer.parseInt(list.get(i + pH1 + 16));
                    percentila95 = Integer.parseInt(list.get(i + pH1 + 31));
                    percentila99 = Integer.parseInt(list.get(i + pH1 + 46));

                    if (addTA < Integer.parseInt(list.get(j)) && c) {
                        percentilaTAmax = Double.parseDouble(list.get(j - pH1));
                        c = false;
                        percentilaTA = "<50th";
                    } else if (addTA >= Integer.parseInt(list.get(j)) &&
                            addTA <= Integer.parseInt(list.get(j + 15)) && c) {
                        percentilaTAmin = Integer.parseInt(list.get(j - pH1));
                        percentilaTAmax = Integer.parseInt(list.get(j - pH1 + 15));
                        c = false;
                        percentilaTA = percentilaTAmin + " - " + percentilaTAmax;

                    } else if (addTA >= Integer.parseInt(list.get(j + 42 + pH1)) && c) {
                        percentilaTA = "> 99th";
                        c = false;
                    }
                }
            }
        }
        return new MyResult(percentilaTA, percentila50, percentila90, percentila95, percentila99);
    }
}