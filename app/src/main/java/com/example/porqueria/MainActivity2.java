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

import android.content.Intent;
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

        //goes back to the first activity
        TextView back=findViewById(R.id.back1);
        back.setOnClickListener(v -> finish());

        //goes further to the 3rd activity~ generate percentiles page
        TextView generate_percentiles_button = findViewById(R.id.generate_percentiles_button);
        generate_percentiles_button.setOnClickListener(view -> {
                Intent intent = new Intent(MainActivity2.this, MainActivity3.class);
                MainActivity2.this.startActivity(intent);
        });

        //calculating zscores
        double zscoreWeightF = getDoubleNumber(zscoreWeight);
        double zscoreHeightF = getDoubleNumber(zscoreHeight);
        double zscoreHeightRo = getDoubleNumber(Porqueria.zscoreHeightRo);
        double zscoreIMC = getDoubleNumber(Porqueria.zscoreIMC);
        double imc = getDoubleNumber(getIMC());

        //calculating percentiles
        double percentilaWeight = getDoubleNumber(zScoreToPercentile(zscoreWeightF));
        double percentilaHeight = getDoubleNumber(zScoreToPercentile(zscoreHeightF));
        double percentilaPC = getDoubleNumber(zScoreToPercentile(zscorePC));
        double procentWeightToHeightRo = getDoubleNumber(Porqueria.procentWeightToHeightRo);
        double greutateaCorespunzatoareTalieiPacientului = getDoubleNumber(Porqueria.greutateaCorespunzatoareTalieiPacientului);
        double percentilaIMC = getDoubleNumber(zScoreToPercentile(zscoreIMC));

        // afisam percentilele de TAS si TAD
        MainActivity2 mainActivity2 = new MainActivity2();
        MyResult result = mainActivity2.getPercentileTAGeneral(addTAS, 0);
        MyResult result1 = mainActivity2.getPercentileTAGeneral(addTAD, 7);

        //afisam rezultatele dorite (cele prelucrate)/*
        TextView resultOfLabour = findViewById(R.id.resultOfLabour);

        resultOfLabour.setText(

                getString(R.string.sex)+": "+addGender+ "\n"+
                getString(R.string.age)+": "+addYears +" "+getString(R.string.years)+", "+ addMonths +" "+ getString(R.string.months)+";"+
                "\n_____________________________________________\n"
                + getString(R.string.Talia)+" " + addHeight + " cm\n" +
                getString(R.string.percentila)+" "+ percentilaHeight +" "+ getString(R.string.varstaSisex) +"\n"+
                "* " + zscoreHeightF +" "+ getString(R.string.DSptVarstaSex) + "\n"+
                "* " + zscoreHeightRo +" "+ getString(R.string.DSptVarstaSexFr) + "\n\n"+
                getString(R.string.TaliaMamei) + addHeightMother + getString(R.string.HeightFather) + " "+addHeightFather + " cm,\n"+
                getString(R.string.ExpectedHeight) +" "+ getTaliaTinta() + " cm" +
                "\n_____________________________________________\n" +
                getString(R.string.Weight) + addWeight + " kg\n" +
                getString(R.string.percentila)+" "+ percentilaWeight +" "+ getString(R.string.varstaSisex) +"\n"+
                "* " + zscoreWeight +" "+ getString(R.string.DSptVarstaSex) + "\n" +
                "* " + procentWeightToHeightRo + " % " + getAnswer(procentWeightToHeightRo) +" "+ getString(R.string.AccordingFrenchStandards) +
                getString(R.string.WeightAccordingHeight) + greutateaCorespunzatoareTalieiPacientului + " kg\n" +
                getString(R.string.SC) + " "+ getDoubleNumber(getSuprafataCorporala()) +" "+ getString(R.string.mp) +
                "\n_____________________________________________\n" +
                getString(R.string.IMC) + " "+imc + "kg/m2, zscore = " + zscoreIMC + getString(R.string.Percentile2) + " "+percentilaIMC + ";\n" +
                getString(R.string.NutritionalIndice) +" "+ getDoubleNumber(getIndiceNutritional()) + getAnswerIN() + "\n" +
                getString(R.string.PonderalIndice) + getDoubleNumber(getIndicePonderal()) + getAnswerIP() +
                "\n_____________________________________________\n" +
                getString(R.string.BP) + Porqueria.addTAS + "/ " + addTAD + " mmHg;\n" +
                getString(R.string.BPpercentile) + result.getFirst() + "; \n" +
                getString(R.string.BPdPercentile) + result1.getFirst() + "; \n" +
                getString(R.string.p50) +" "+ result.getSecond() + "/ " + result1.getSecond() + "; \n" +
                getString(R.string.p90) + " "+result.getThird() + "/ " + result1.getThird() + "; \n" +
                getString(R.string.p95) + " "+result.getForth() + "/ " + result1.getForth() + "; \n" +
                getString(R.string.p99) + " "+result.getFifth() + "/ " + result1.getFifth() + "." +
                "\n_____________________________________________\n" +
                getString(R.string.headCircumference) +" "+ addPerimeter + " cm, " + getDoubleNumber(zscorePC) + " DS, p" + getDoubleNumber(percentilaPC) + ";" +
                "\n_____________________________________________\n" +
                "\n" +
                getString(R.string.finalStatement));


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

    public String getAnswer(Double procent) {
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
            raspuns = getString(R.string.greutate_talie_sex);
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
                raspuns = getString(R.string.paratrofic);
            }
            if (getIndicePonderal() > 0.89 && getIndicePonderal() <= 1.1) {
                raspuns = getString(R.string.eutrofic);
            }
            if (getIndicePonderal() > 0.76 && getIndicePonderal() <= 0.89) {
                raspuns = getString(R.string.malnutritieI);
            }
            if (getIndicePonderal() > 0.60 && getIndicePonderal() <= 0.75) {
                raspuns = getString(R.string.malnutritieII);
            }
            if (getIndicePonderal() <= 0.60 && getIndicePonderal() != 0) {
                raspuns = getString(R.string.malnutritieIII);
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
                raspuns = getString(R.string.paratrofic);
            }
            if (getIndiceNutritional() > 0.89 && getIndiceNutritional() <= 1.1) {
                raspuns = getString(R.string.eutrofic);
            }
            if (getIndiceNutritional() > 0.80 && getIndiceNutritional() <= 0.89) {
                raspuns = getString(R.string.malnutritieI);
            }
            if (getIndiceNutritional() > 0.70 && getIndiceNutritional() <= 0.80) {
                raspuns = getString(R.string.malnutritieII);
            }
            if (getIndiceNutritional() <= 0.70 && getIndiceNutritional() != 0) {
                raspuns = getString(R.string.malnutritieIII);
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
    private ArrayList<String> getList() {
        ArrayList<String> list = new ArrayList<>();
        if (addGender.equals("Feminin")) {
            list.addAll(Porqueria.listPercentileTAF);
        } else list.addAll(Porqueria.listPercentileTAM);
        return list;
    }

    // get the percentile TAS for a given TA (for Feminine or Masculine)
    // value - if 0 then outputs TAS values, if 7 then outputs TAD values
    public MyResult getPercentileTAGeneral(int addTA, int value) {
        int percentila50 = 0, percentila90 = 0, percentila95 = 0, percentila99 = 0;
        String percentilaTA = null;
        boolean c = true;
        int pH1 = getIntervalPercentileForHeight() + value;
        //if no TA is introduced, returns 0
        if (addTA == 0) return new MyResult("0", 0, 0, 0, 0);
        //getting the list
        ArrayList<String> list = new ArrayList<>();
        list.addAll(getList());
        //finding the percentile values
        for (int i = 0; i < list.size(); i += 61) {
            if (Integer.parseInt(list.get(i)) == addYears) {
                percentila50 = Integer.parseInt(list.get(i + pH1 + 1));
                percentila90 = Integer.parseInt(list.get(i + pH1 + 16));
                percentila95 = Integer.parseInt(list.get(i + pH1 + 31));
                percentila99 = Integer.parseInt(list.get(i + pH1 + 46));

                // finding the percentile of TA
                List<Integer> listPercentiles= Arrays.asList(percentila50, percentila90, percentila95, percentila99);
                for (int j = 0; j < listPercentiles.size(); j++) {
                    if (addTA == listPercentiles.get(j) && c) {
                        List<String> stringPercentiles= Arrays.asList("50th","90th","95th","99th");
                        percentilaTA = stringPercentiles.get(j);
                        c = false;
                        }
                    if (addTA < listPercentiles.get(j) && c) {
                        List<String> stringPercentiles2= Arrays.asList("<50th","50th-90th","90th-95th","95th-99th");
                        percentilaTA = stringPercentiles2.get(j);
                        c = false;
                    }
                    if (addTA > percentila99 && c) {
                        percentilaTA = ">99th";
                        c= false;
                    }
                }
            }
        }
        return new MyResult(percentilaTA, percentila50, percentila90, percentila95, percentila99);
    }
}