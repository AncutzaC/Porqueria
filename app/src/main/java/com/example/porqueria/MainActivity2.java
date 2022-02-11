package com.example.porqueria;

import static android.text.Html.FROM_HTML_MODE_LEGACY;
import static com.example.porqueria.Porqueria.addBirthWeight;
import static com.example.porqueria.Porqueria.addGender;
import static com.example.porqueria.Porqueria.addHeight;
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

import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.text.HtmlCompat;

import org.apache.commons.math3.distribution.NormalDistribution;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
        double imc = getDoubleNumber(getimc());


        double percentilaWeight = getDoubleNumber(zScoreToPercentileWeight(zscoreWeightF));
        double percentilaHeight = getDoubleNumber(zScoreToPercentileHeight(zscoreHeightF));
        double percentilaPC = getDoubleNumber(zScoreToPercentilePC(zscorePC1));
        double procentWeightToHeightRo = getDoubleNumber(Porqueria.procentWeightToHeightRo);
        double greutateaCorespunzatoareTalieiPacientului = getDoubleNumber(Porqueria.greutateaCorespunzatoareTalieiPacientului);
        double percentilaIMC = getDoubleNumber(zScoreToPercentileIMC(zscoreIMC));

        // afisam percentilele de TAS si TAD
        MainActivity2 mainActivity2 = new MainActivity2();
        MyResult result = mainActivity2.getTASpercentileTAS();
        MyResult result1 = mainActivity2.getTASpercentileTAD();


        //afisam rezultatele dorite (cele prelucrate)/*
        TextView resultOfLabour = (TextView) findViewById(R.id.resultOfLabour);


        resultOfLabour.setText(

                "Sex: " + addGender + "\n"+
                        "Varsta: "+ addYears + " ani, " + addMonths + " luni;\n\n" +
                        "_____________________________________________\n"
                        + "Talia: " + addHeight + "cm\n" +
                        "* percentila: " + percentilaHeight + " pt varsta si sex (cf CDC, WHO);\n" +
                        "* " + zscoreHeightF + " DS pt varsta si sex (cf CDC, WHO);\n" +
                        "* " + zscoreHeightRo + "DS pt varsta si sex (cf standardelor franceze '97)\n\n" +
                        "_____________________________________________\n" +
                        "Greutatea: " + addWeight + "kg\n" +
                        "* percentila: " + percentilaWeight + " pt varsta si sex (cf CDC, WHO);\n"
                        + "* " + zscoreWeightF + " DS pt varsta si sex (cf CDC, WHO)\n" +
                        "* " + procentWeightToHeightRo + " % " + getAnswer(procentWeightToHeightRo) + ", cf std franceze '97; -->> " +
                        "Greutatea corespunzatoare taliei pacientului este: " + greutateaCorespunzatoareTalieiPacientului + " kg\n" +
                        "* S.c. = " + getDoubleNumber(getSuprafataCorporala()) + "mp;\n" +
                        "_____________________________________________\n" +
                        "IMC = " + imc + "kg/m2, zscore = " + zscoreIMC + ", percentila " + percentilaIMC + ";\n" +
                        "Indice nutritional = " + getDoubleNumber(getIndiceNutritional()) +  getAnswerIN() + "\n" +
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
                        "PC = " + addPerimeter + " cm, " + zscorePC1 + " DS, p" + percentilaPC + ";\n" +
                        "_____________________________________________\n" +
                        "\n\n\n\n\n\n\n\n" +
                        "Conform CDC pt varsta sub 2 ani si conform WHO pt 2-20 ani.");


    }
    private String getColoredSpanned(String text) {
        //transforma textul in rosu
        String input = "<font color=#800000>" + text + "</font>";
        return input;
    }

    public static double getDoubleNumber(double desiredNumber) {
        double bigDecimal2 = 0;
        if (desiredNumber ==0 || Double.isInfinite(desiredNumber) || Double.isNaN(desiredNumber) ){bigDecimal2 = 0; return bigDecimal2;}
        if (desiredNumber != 0){
        BigDecimal bigDecimal = new BigDecimal(desiredNumber);
        BigDecimal bigDecimal1 = bigDecimal.setScale(2, RoundingMode.HALF_UP);
        bigDecimal2 = bigDecimal1.doubleValue();
    } return bigDecimal2;
    }

    public static double zScoreToPercentileWeight(double zScore) {
        double number =0;
        if (zScore == 0){return number;}
        if (zScore != 0) {
            NormalDistribution dist = new NormalDistribution();
            number = dist.cumulativeProbability(zScore) * 100;
        }
        return number;
    }

    public static double zScoreToPercentileHeight(double zScore) {
        double number =0;
        if (zScore == 0){return number;}
        if (zScore != 0) {
            NormalDistribution dist = new NormalDistribution();
            number = dist.cumulativeProbability(zScore) * 100;
        }
        return number;
    }

    public static double zScoreToPercentileIMC(double zScore) {
        double number =0;
        if (zScore == 0){return number;}
        if (zScore != 0) {
            NormalDistribution dist = new NormalDistribution();
            number = dist.cumulativeProbability(zScore) * 100;
        }
        return number;
    }

    public static double zScoreToPercentilePC(double zScore) {
        double number =0;
        if (zScore == 0){return number;}
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

    public double getimc() {
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
            if (getIndicePonderal() ==0){
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
            if (getIndiceNutritional() ==0){
                raspuns = "";
            }
            return raspuns;
        }
        return "N/A";
    }

    public double getSuprafataCorporala() {
        if (addWeight == 0){return 0;}
        else return (addWeight * 4 + 7) / (addWeight + 90);
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

    int percentila50F, percentila90F, percentila95F, percentila99F;
    int percentila50FD, percentila90FD, percentila95FD, percentila99FD;
    double percentilaTASmax, percentilaTASmin;
    double percentilaTADmax, percentilaTADmin;
    String percentilaTASF, percentilaTADF;
    int pH1;

    public int getpH1Value() {
        boolean b = true;
        List<Integer> listPercentileHeight = Arrays.asList(0, 5, 10, 25, 50, 75, 90, 95, 100);
        for (int k = 0; k < listPercentileHeight.size(); k++) {
            if (getDoubleNumber(zScoreToPercentileHeight((zscoreHeight))) <= listPercentileHeight.get(k) && b) {
                pH1 = k - 1;
                b = false;
            }
        }
        return pH1;
    }

    public MyResult getTASpercentileTAS() {
        boolean c = true;
        int pH1 = getpH1Value() + 1;
        if (addTAS == 0) return new MyResult("0", 0, 0, 0, 0);
        if (addTAS != 0) {
            if (addGender.equals("Feminin")) {
                for (int i = 0; i < listPercentileTASF.size(); i += 61) {
                    if (Integer.parseInt(listPercentileTASF.get(i)) == addYears) {
                        for (int j = i + 1 + pH1; j < i + 61; j += 15) {
                            percentila50F = Integer.parseInt(listPercentileTASF.get(i + pH1 + 1));
                            percentila90F = Integer.parseInt(listPercentileTASF.get(i + pH1 + 16));
                            percentila95F = Integer.parseInt(listPercentileTASF.get(i + pH1 + 31));
                            percentila99F = Integer.parseInt(listPercentileTASF.get(i + pH1 + 46));

                            if (addTAS < Integer.parseInt(listPercentileTASF.get(j)) && c) {
                                percentilaTASmax = Double.parseDouble(listPercentileTASF.get(j - pH1));
                                c = false;
                                percentilaTASF = "<50th";
                            } else if (addTAS >= Integer.parseInt(listPercentileTASF.get(j)) &&
                                    addTAS <= Integer.parseInt(listPercentileTASF.get(j + 15)) && c) {
                                percentilaTASmin = Integer.parseInt(listPercentileTASF.get(j - pH1));
                                percentilaTASmax = Integer.parseInt(listPercentileTASF.get(j - pH1 + 15));
                                c = false;
                                percentilaTASF = percentilaTASmin + " - " + percentilaTASmax;

                            } else if (addTAS >= Integer.parseInt(listPercentileTASF.get(j + 42 + pH1)) && c) {
                                percentilaTASF = "> 99th";
                                c = false;

                            }
                        }

                    }
                }
            }
            if (addGender.equals("Masculin")) {
                for (int i = 0; i < listPercentileTASM.size(); i += 61) {
                    if (Integer.parseInt(listPercentileTASM.get(i)) == addYears) {
                        for (int j = i + 1 + pH1; j < i + 61; j += 15) {
                            percentila50F = Integer.parseInt(listPercentileTASM.get(i + pH1 + 1));
                            percentila90F = Integer.parseInt(listPercentileTASM.get(i + pH1 + 16));
                            percentila95F = Integer.parseInt(listPercentileTASM.get(i + pH1 + 31));
                            percentila99F = Integer.parseInt(listPercentileTASM.get(i + pH1 + 46));

                            if (addTAS < Integer.parseInt(listPercentileTASM.get(j)) && c) {
                                c = false;
                                percentilaTASF = "<50th";
                            } else if (addTAS >= Integer.parseInt(listPercentileTASM.get(j)) &&
                                    addTAS <= Integer.parseInt(listPercentileTASM.get(j + 15)) && c) {
                                percentilaTASmin = Integer.parseInt(listPercentileTASM.get(j - pH1));
                                percentilaTASmax = Integer.parseInt(listPercentileTASM.get(j - pH1 + 15));
                                c = false;
                                percentilaTASF = percentilaTASmin + " - " + percentilaTASmax;

                            } else if (addTAS >= Integer.parseInt(listPercentileTASM.get(j + 42 + pH1)) && c) {
                                percentilaTASF = "> 99th";
                                c = false;
                            }
                        }

                    }
                }
            }
        }
        return new MyResult(percentilaTASF, percentila50F, percentila90F, percentila95F, percentila99F);
    }

    public MyResult getTASpercentileTAD() {
        int pH1D = getpH1Value() + 9;
        boolean c1 = true;
        if (addTAD == 0) return new MyResult("0", 0, 0, 0, 0);
        if (addTAD != 0) {
            if (addGender.equals("Masculin")) {
                for (int i = 0; i < listPercentileTASM.size(); i += 61) {
                    if (Integer.parseInt(listPercentileTASM.get(i)) == addYears && addGender.equals("Feminin")) {
                        for (int j = i + 1 + pH1D; j < i + 61; j += 15) {
                            percentila50FD = Integer.parseInt(listPercentileTASM.get(i + pH1D - 1));
                            percentila90FD = Integer.parseInt(listPercentileTASM.get(i + pH1D + 14));
                            percentila95FD = Integer.parseInt(listPercentileTASM.get(i + pH1D + 29));
                            percentila99FD = Integer.parseInt(listPercentileTASM.get(i + pH1D + 44));

                            if (addTAD < Integer.parseInt(listPercentileTASM.get(j)) && c1) {
                                percentilaTADmax = Integer.parseInt(listPercentileTASM.get(j - pH1D));
                                c1 = false;
                                percentilaTADF = "<50th";
                            } else if (addTAD >= Integer.parseInt(listPercentileTASM.get(j)) &&
                                    addTAD <= Integer.parseInt(listPercentileTASM.get(j + 15)) && c1) {
                                percentilaTADmin = Integer.parseInt(listPercentileTASM.get(j - pH1D));
                                percentilaTADmax = Integer.parseInt(listPercentileTASM.get(j - pH1D + 15));
                                c1 = false;
                                percentilaTADF = percentilaTADmin + " - " + percentilaTADmax;

                            } else if (addTAD >= Integer.parseInt(listPercentileTASM.get(j + 42 + pH1D)) && c1) {
                                percentilaTADF = "> 99th";
                                c1 = false;

                            }
                        }

                    }
                }
            }
            if (addGender.equals("Feminin")) {
                for (int i = 0; i < listPercentileTASF.size(); i += 61) {
                    if (Integer.parseInt(listPercentileTASF.get(i)) == addYears && addGender.equals("Feminin")) {
                        for (int j = i + 1 + pH1D; j < i + 61; j += 15) {
                            percentila50FD = Integer.parseInt(listPercentileTASF.get(i + pH1D - 1));
                            percentila90FD = Integer.parseInt(listPercentileTASF.get(i + pH1D + 14));
                            percentila95FD = Integer.parseInt(listPercentileTASF.get(i + pH1D + 29));
                            percentila99FD = Integer.parseInt(listPercentileTASF.get(i + pH1D + 44));

                            if (addTAD < Integer.parseInt(listPercentileTASF.get(j)) && c1) {
                                percentilaTADmax = Integer.parseInt(listPercentileTASF.get(j - pH1D));
                                c1 = false;
                                percentilaTADF = "<50th";
                            } else if (addTAD >= Integer.parseInt(listPercentileTASF.get(j)) &&
                                    addTAD <= Integer.parseInt(listPercentileTASF.get(j + 15)) && c1) {
                                percentilaTADmin = Integer.parseInt(listPercentileTASF.get(j - pH1D));
                                percentilaTADmax = Integer.parseInt(listPercentileTASF.get(j - pH1D + 15));
                                c1 = false;
                                percentilaTADF = percentilaTADmin + " - " + percentilaTADmax;

                            } else if (addTAD >= Integer.parseInt(listPercentileTASF.get(j + 42 + pH1D)) && c1) {
                                percentilaTADF = "> 99th";
                                c1 = false;

                            }
                        }

                    }
                }
            }
        }
        return new MyResult(percentilaTADF, percentila50FD, percentila90FD, percentila95FD, percentila99FD);
    }

    List<String> listPercentileTASM = Arrays.asList(

            "1","50","80","81","83","85","87","88","89","34","35","36","37","38","39","39","90","94","95","97","99","100","102","103","49","50","51","52","53","53","54","95","98","99","101","103","104","106","106","54","54","55","56","57","58","58","99","105","106","108","110","112","113","114","61","62","63","64","65","66","66",
            "2","50","84","85","87","88","90","92","92","39","40","41","42","43","44","44","90","97","99","100","102","104","105","106","54","55","56","57","58","58","59","95","101","102","104","106","108","109","110","59","59","60","61","62","63","63","99","109","110","111","113","115","117","117","66","67","68","69","70","71","71",
            "3","50","86","87","89","91","93","94","95","44","44","45","46","47","48","48","90","100","101","103","105","107","108","109","59","59","60","61","62","63","63","95","104","105","107","109","110","112","113","63","63","64","65","66","67","67","99","111","112","114","116","118","119","120","71","71","72","73","74","75","75",
            "4","50","88","89","91","93","95","96","97","47","48","49","50","51","51","52","90","102","103","105","107","109","110","111","62","63","64","65","66","66","67","95","106","107","109","111","112","114","115","66","67","68","69","70","71","71","99","113","114","116","118","120","121","122","74","75","76","77","78","78","79",
            "5","50","90","91","93","95","96","98","98","50","51","52","53","54","55","55","90","104","105","106","108","110","111","112","65","66","67","68","69","69","70","95","108","109","110","112","114","115","116","69","70","71","72","73","74","74","99","115","116","118","120","121","123","123","77","78","79","80","81","81","82",
            "6","50","91","92","94","96","98","99","100","53","53","54","55","56","57","57","90","105","106","108","110","111","113","113","68","68","69","70","71","72","72"," 95","109","110","112","114","115","117","117","72","72","73","74","75","76","76","99","116","117","119","121","123","124","125","80","80","81","82","83","84","84",
            "7","50","92","94","95","97","99","100","101","55","55","56","57","58","59","59","90","106","107","109","111","113","114","115","70","70","71","72","73","74","74","95","110","111","113","115","117","118","119","74","74","75","76","77","78","78","99","117","118","120","122","124","125","126","82","82","83","84","85","86","86",
            "8","50","94","95","97","99","100","102","102","56","57","58","59","60","60","61","90","107","109","110","112","114","115","116","71","72","72","73","74","75","76","95","111","112","114","116","118","119","120","75","76","77","78","79","79","80","99","119","120","122","123","125","127","127","83","84","85","86","87","87","88",
            "9","50","95","96","98","100","102","103","104","57","58","59","60","61","61","62","90","109","110","112","114","115","117","118","72","73","74","75","76","76","77","95","113","114","116","118","119","121","121","76","77","78","79","80","81","81","99","120","121","123","125","127","128","129","84","85","86","87","88","88","89",
            "10","50","97","98","100","102","103","105","106","58","59","60","61","61","62","63","90","111","112","114","115","117","119","119","73","73","74","75","76","77","78","95","115","116","117","119","121","122","123","77","78","79","80","81","81","82","99","122","123","125","127","128","130","130","85","86","86","88","88","89","90",
            "11","50","99","100","102","104","105","107","107","59","59","60","61","62","63","63","90","113","114","115","117","119","120","121","74","74","75","76","77","78","78","95","117","118","119","121","123","124","125","78","78","79","80","81","82","82","99","124","125","127","129","130","132","132","86","86","87","88","89","90","90",
            "12","50","101","102","104","106","108","109","110","59","60","61","62","63","63","64","90","115","116","118","120","121","123","123","74","75","75","76","77","78","79","95","119","120","122","123","125","127","127","78","79","80","81","82","82","83","99","126","127","129","131","133","134","135","86","87","88","89","90","90","91",
            "13","50","104","105","106","108","110","111","112","60","60","61","62","63","64","64","90","117","118","120","122","124","125","126","75","75","76","77","78","79","79","95","121","122","124","126","128","129","130","79","79","80","81","82","83","83","99","128","130","131","133","135","136","137","87","87","88","89","90","91","91",
            "14","50","106","107","109","111","113","114","115","60","61","62","63","64","65","65","90","120","121","123","125","126","128","128","75","76","77","78","79","79","80","95","124","125","127","128","130","132","132","80","80","81","82","83","84","84","99","131","132","134","136","138","139","140","87","88","89","90","91","92","92",
            "15","50","109","110","112","113","115","117","117","61","62","63","64","65","66","66","90","122","124","125","127","129","130","131","76","77","78","79","80","80","81","95","126","127","129","131","133","134","135","81","81","82","83","84","85","85","99","134","135","136","138","140","142","142","88","89","90","91","92","93","93",
            "16","50","111","112","114","116","118","119","120","63","63","64","65","66","67","67","90","125","126","128","130","131","133","134","78","78","79","80","81","82","82","95","129","130","132","134","135","137","137","82","83","83","84","85","86","87","99","136","137","139","141","143","144","145","90","90","91","92","93","94","94",
            "17","50","114","115","116","118","120","121","122","65","66","66","67","68","69","70","90","127","128","130","132","134","135","136","80","80","81","82","83","84","84","95","131","132","134","136","138","139","140","84","85","86","87","87","88","89","99","139","140","141","143","145","146","147","92","93","93","94","95","96","97"
    );
    List<String> listPercentileTASF = Arrays.asList(
            "1","50","83","84","85","86","88","89","90","38","39","39","40","41","41","42","90","97","97","98","100","101","102","103","52","53","53","54","55","55","56","95","100","101","102","104","105","106","107","56","57","57","58","59","59","60","99","108","108","109","111","112","113","114","64","64","65","65","66","67","67",
            "2","50","85","85","87","88","89","91","91","43","44","44","45","46","46","47","90","98","99","100","101","103","104","105","57","58","58","59","60","61","61","95","102","103","104","105","107","108","109","61","62","62","63","64","65","65","99","109","110","111","112","114","115","116","69","69","70","70","71","72","72",
            "3","50","86","87","88","89","91","92","93","47","48","48","49","50","50","51","90","100","100","102","103","104","106","106","61","62","62","63","64","64","65","95","104","104","105","107","108","109","110","65","66","66","67","68","68","69","99","111","111","113","114","115","116","117","73","73","74","74","75","76","76",
            "4","50","88","88","90","91","92","94","94","50","50","51","52","52","53","54","90","101","102","103","104","106","107","108","64","64","65","66","67","67","68","95","105","106","107","108","110","111","112","68","68","69","70","71","71","72","99","112","113","114","115","117","118","119","76","76","76","77","78","79","79",
            "5","50","89","90","91","93","94","95","96","52","53","53","54","55","55","56","90","103","103","105","106","107","109","109","66","67","67","68","69","69","70","95","107","107","108","110","111","112","113","70","71","71","72","73","73","74","99","114","114","116","117","118","120","120","78","78","79","79","80","81","81",
            "6","50","91","92","93","94","96","97","98","54","54","55","56","56","57","58","90","104","105","106","108","109","110","111","68","68","69","70","70","71","72","95","108","109","110","111","113","114","115","72","72","73","74","74","75","76","99","115","116","117","119","120","121","122","80","80","80","81","82","83","83",
            "7","50","93","93","95","96","97","99","99","55","56","56","57","58","58","59","90","106","107","108","109","111","112","113","69","70","70","71","72","72","73","95","110","111","112","113","115","116","116","73","74","74","75","76","76","77","99","117","118","119","120","122","123","124","81","81","82","82","83","84","84",
            "8","50","95","95","96","98","99","100","101","57","57","57","58","59","60","60","90","108","109","110","111","113","114","114","71","71","71","72","73","74","74","95","112","112","114","115","116","118","118","75","75","75","76","77","78","78","99","119","120","121","122","123","125","125","82","82","83","83","84","85","86",
            "9","50","96","97","98","100","101","102","103","58","58","58","59","60","61","61","90","110","110","112","113","114","116","116","72","72","72","73","74","75","75","95","114","114","115","117","118","119","120","76","76","76","77","78","79","79","99","121","121","123","124","125","127","127","83","83","84","84","85","86","87",
            "10","50","98","99","100","102","103","104","105","59","59","59","60","61","62","62","90","112","112","114","115","116","118","118","73","73","73","74","75","76","76","95","116","116","117","119","120","121","122","77","77","77","78","79","80","80","99","123","123","125","126","127","129","129","84","84","85","86","86","87","88",
            "11","50","100","101","102","103","105","106","107","60","60","60","61","62","63","63","90","114","114","116","117","118","119","120","74","74","74","75","76","77","77","95","118","118","119","121","122","123","124","78","78","78","79","80","81","81","99","125","125","126","128","129","130","131","85","85","86","87","87","88","89",
            "12","50","102","103","104","105","107","108","109","61","61","61","62","63","64","64","90","116","116","117","119","120","121","122","75","75","75","76","77","78","78","95","119","120","121","123","124","125","126","79","79","79","80","81","82","82","99","127","127","128","130","131","132","133","86","86","87","88","88","89","90",
            "13","50","104","105","106","107","109","110","110","62","62","62","63","64","65","65","90","117","118","119","121","122","123","124","76","76","76","77","78","79","79","95","121","122","123","124","126","127","128","80","80","80","81","82","83","83","99","128","129","130","132","133","134","135","87","87","88","89","89","90","91",
            "14","50","106","106","107","109","110","111","112","63","63","63","64","65","66","66","90","119","120","121","122","124","125","125","77","77","77","78","79","80","80","95","123","123","125","126","127","129","129","81","81","81","82","83","84","84","99","130","131","132","133","135","136","136","88","88","89","90","90","91","92",
            "15","50","107","108","109","110","111","113","113","64","64","64","65","66","67","67","90","120","121","122","123","125","126","127","78","78","78","79","80","81","81","95","124","125","126","127","129","130","131","82","82","82","83","84","85","85","99","131","132","133","134","136","137","138","89","89","90","91","91","92","93",
            "16","50","108","108","110","111","112","114","114","64","64","65","66","66","67","68","90","121","122","123","124","126","127","128","78","78","79","80","81","81","82","95","125","126","127","128","130","131","132","82","82","83","84","85","85","86","99","132","133","134","135","137","138","139","90","90","90","91","92","93","93",
            "17","50","108","109","110","111","113","114","115","64","65","65","66","67","67","68","90","122","122","123","125","126","127","128","78","79","79","80","81","81","82","95","125","126","127","129","130","131","132","82","83","83","84","85","85","86","99","133","133","134","136","137","138","139","90","90","91","91","92","93","93"
    );
}