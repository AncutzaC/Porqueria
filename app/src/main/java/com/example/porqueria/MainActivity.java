package com.example.porqueria;

import static com.example.porqueria.MainActivity2.getDoubleNumber;
import static com.example.porqueria.Porqueria.addGender;
import static com.example.porqueria.Porqueria.addIMC;
import static com.example.porqueria.Porqueria.addTAS;
import static com.example.porqueria.Porqueria.addTotalMonths;
import static com.example.porqueria.Porqueria.addYears;
import static com.example.porqueria.Porqueria.birthdayresultGlobal;
import static java.lang.String.valueOf;

import android.app.DatePickerDialog;
import org.joda.time.Period;
import org.joda.time.PeriodType;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.InputFilter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private Button resetButton, calculateWhatever, changeLanguage, birthdaydata;
    private EditText addYears, addMonths, addWeight, addHeight, addTAS, addTAD, addPerimeter,
            addBirthWeight, addHeightMother, addHeightFather;
    private TextView birthdayresult;
    private RadioButton checkFemale, checkMale, gender;
    private RadioGroup radioGroup;

    private DatePicker datePicker;
    private Calendar calendar;
    private int year;
    private int month;
    private int day;
    DatePickerDialog.OnDateSetListener dateSetListener;

    protected Porqueria app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLocale();
        setContentView(R.layout.activity_main);

        // import variables from xml and set boundaries
        calculateWhatever = findViewById(R.id.calculateWhatever);
        resetButton = findViewById(R.id.resetButton);
        changeLanguage = findViewById(R.id.changeLanguageButton);
        changeLanguage.setOnClickListener(view -> {
            //show AlertDialog to display list of languages, one can be selected
            showChangeLanguageDialog();
        });

        // import button
        birthdaydata = findViewById(R.id.birthdaydata);
        birthdayresult = findViewById(R.id.birthdayresult);

        // import buttons
        // set min and max for each category

        addYears = findViewById(R.id.addYears);
        EditText et = addYears;
        et.setFilters(new InputFilter[]{new InputFilterMinMax("0", "18")});

        addMonths = findViewById(R.id.addMonths);
        EditText et1 = addMonths;
        et1.setFilters(new InputFilter[]{new InputFilterMinMax("0", "11")});

        addWeight = findViewById(R.id.addWeight);
        EditText et2 = addWeight;
        et2.setFilters(new InputFilter[]{new InputFilterMinMax("0", "150")});

        addHeight = findViewById(R.id.addHeight);
        EditText et3 = addHeight;
        et3.setFilters(new InputFilter[]{new InputFilterMinMax("0", "210")});

        addTAS = findViewById(R.id.addTAS);
        EditText et4 = addTAS;
        et4.setFilters(new InputFilter[]{new InputFilterMinMax("0", "250")});

        addTAD = findViewById(R.id.addTAD);
        EditText et5 = addTAD;
        et5.setFilters(new InputFilter[]{new InputFilterMinMax("0", "250")});

        addPerimeter = findViewById(R.id.addPerimeter);
        EditText et6 = addPerimeter;
        et6.setFilters(new InputFilter[]{new InputFilterMinMax("0", "60")});

        addBirthWeight = findViewById(R.id.addBirthWeight);
        EditText et7 = addBirthWeight;
        et7.setFilters(new InputFilter[]{new InputFilterMinMax("0", "6000")});

        addHeightMother = findViewById(R.id.addHeight2);
        EditText et8 = addHeightMother;
        et8.setFilters(new InputFilter[]{new InputFilterMinMax("0", "200")});

        addHeightFather = findViewById(R.id.addHeight3);
        EditText et9 = addHeightFather;
        et9.setFilters(new InputFilter[]{new InputFilterMinMax("0", "200")});

        checkFemale = findViewById(R.id.checkFemale);
        checkMale = findViewById(R.id.checkMale);
        radioGroup = findViewById(R.id.radioGroup);


        //add functionality to the buttons
        // selecting birthday date and returning it to birthdayresult
        birthdaydata.setOnClickListener(view -> {
            calendar = Calendar.getInstance();
            year = calendar.get(Calendar.YEAR);
            month = calendar.get(Calendar.MONTH);

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

            // to set the current date as by default
            String date = simpleDateFormat.format(Calendar.getInstance().getTime());

            // action to be performed when birthdaydata button is clicked
            birthdaydata.setOnClickListener(view1 -> {
                // date picker dialog is used and its style and color are also passed
                DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth, dateSetListener, year, month, day);
                // to set background for datepicker
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            });

            // it is used to set the date which user selects
            dateSetListener = (view12, year, month, day) -> {
                // here month+1 is used so that actual month number can be displayed,
                // otherwise it starts from 0 and it shows 1 number less for every month;
                // example- for january month=0
                month = month + 1;
                String date1 = day + "/" + month + "/" + year;
                birthdayresult.setText(date1);
                Porqueria.birthdayresultGlobal = birthdayresult.getText().toString();
            };
        });


        calculateWhatever.setOnClickListener(view -> {
            try {
                openActivity2();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        resetButton.setOnClickListener(view -> {
            addYears.setText("");
            addMonths.setText("");
            addHeight.setText("");
            addWeight.setText("");
            addTAS.setText("");
            addTAD.setText("");
            addPerimeter.setText("");
            addBirthWeight.setText("");
            addHeightMother.setText("");
            addHeightFather.setText("");
            birthdayresult.setText("");
        });

        app = (Porqueria) getApplication();

    }

    private void showChangeLanguageDialog() {
        // array of languages to display in alert display
        final String[] listItem = {"Romana", "English", "Francais", "Español"};
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Choose language: ");
        builder.setSingleChoiceItems(listItem, -1, (dialogInterface, i) -> {
            if (i == 0) {
                setLocale("ro");
                recreate();
            }
            if (i == 1) {
                setLocale("en");
                recreate();
            }
            if (i == 2) {
                setLocale("fr");
                recreate();
            }

            if (i == 3) {
                setLocale("es");
                recreate();
            }
            // dismiss alert dialog when language selected
            dialogInterface.dismiss();
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void setLocale(String lang) {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration configuration = new Configuration();
        configuration.locale = locale;
        getBaseContext().getResources().updateConfiguration(configuration, getBaseContext().getResources().getDisplayMetrics());

        // save data to shared preferences
        SharedPreferences.Editor editor = getSharedPreferences("Settings", MODE_PRIVATE).edit();
        editor.putString("My_Lang", lang);
        editor.apply();
    }

    //load language saved in shared preferences
    public void loadLocale() {
        SharedPreferences preferences = getSharedPreferences("Settings", MainActivity.MODE_PRIVATE);
        String language = preferences.getString("My_Lang", "");
        setLocale(language);
    }

    // metoda care deschide fereastra 2 si transmite datele din fereastra 1 in fereastra 2
    public void openActivity2() throws IOException {
        //extragem sex-ul
        int getGender = radioGroup.getCheckedRadioButtonId();
        gender = findViewById(getGender);

        if (Objects.requireNonNull(addYears.getText()).toString().trim().isEmpty())
            addYears.setText("0");
        if (Objects.requireNonNull(addMonths.getText()).toString().trim().isEmpty())
            addMonths.setText("0");


        //get month and year from calendar
        // code for calculating age from hand input or date selection
        MainActivity mainActivity = new MainActivity();
        MainActivity.DateSelector dateSelector = mainActivity.getDateFromCalendar();

        int tempYears = Integer.parseInt(addYears.getText().toString());
        int tempMonths = Integer.parseInt(addMonths.getText().toString());

        Porqueria.addYears = tempYears;
        Porqueria.addMonths = tempMonths;
        Porqueria.addTotalMonths = (tempYears *12)+tempMonths;

        if (tempYears == 0 && tempMonths == 0 && dateSelector.yearsCalendar!= 0) {
                Porqueria.addTotalMonths = (dateSelector.yearsCalendar * 12) + dateSelector.monthsCalendar;
                Porqueria.addYears = dateSelector.yearsCalendar;
                Porqueria.addMonths = dateSelector.monthsCalendar;
            }
            else if (0 == tempYears && tempMonths == 0 && dateSelector.monthsCalendar == 0) {
                addTotalMonths=0;
                Porqueria.addYears=0;
                Porqueria.addMonths=0;
            }

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

        if (Objects.requireNonNull(addHeightMother.getText()).toString().trim().isEmpty())
            addHeightMother.setText("0");
        Porqueria.addHeightMother = Double.parseDouble(addHeightMother.getText().toString());

        if (Objects.requireNonNull(addHeightFather.getText()).toString().trim().isEmpty())
            addHeightFather.setText("0");
        Porqueria.addHeightFather = Double.parseDouble(addHeightFather.getText().toString());

        //preparing parameters

        addGender = gender.getText().toString();

        Porqueria.addIMC = (Porqueria.addWeight * 10000) / (Porqueria.addHeight * Porqueria.addHeight);

        Porqueria.zscoreHeightRo = getZscoreHeightRo();
        Porqueria.procentWeightToHeightRo = getProcentWeightToHeightRo();
        Porqueria.greutateaCorespunzatoareTalieiPacientului = getGreutateCorespunzatoareTalieiPacientului();

        // saving the lists in the Porqueria class
        Porqueria.listPercentilePC = getList("listPercentilePC");
        Porqueria.listWeightForAge = getList("listGreutatePtVarsta");
        Porqueria.listHeightForAge = getList("listTaliePtVarsta");
        Porqueria.listIMC = getList("listIMC");
        Porqueria.listPercentileTAF = getList("listPercentileTAF");
        Porqueria.listPercentileTAM = getList("listPercentileTAM");

        // calculating specific zscores using getZscoreGeneral and a maximal age
        Porqueria.zscorePC = getDoubleNumber(
                getZscoreWithMaximalAge(Porqueria.addPerimeter, Porqueria.listPercentilePC, 36));

        // calculating specific zscores using getZscoreGeneral
        Porqueria.zscoreWeight = getDoubleNumber(
                getZscoreGeneral(Porqueria.addWeight, Porqueria.listWeightForAge));
        Porqueria.zscoreHeight = getDoubleNumber(
                getZscoreGeneral(Porqueria.addHeight, Porqueria.listHeightForAge));
        Porqueria.zscoreIMC = getDoubleNumber(
                getZscoreGeneral(addIMC, Porqueria.listHeightForAge));

        Intent intent = new Intent(MainActivity.this, MainActivity2.class);
        startActivity(intent);
    }


    // code for accessing the .txt from the assets
    private ArrayList<String> getList(String filename) throws IOException {
        ArrayList<String> myDict = new ArrayList<String>();
        InputStream is = getApplicationContext().getAssets().open(filename);
        BufferedReader r = new BufferedReader(new InputStreamReader(is));
        String line;
        while ((line = r.readLine()) != null) {
            myDict.add(line);
        }
        return myDict;
    }

    // getting gender : female =2, male =1
    public int getGenderNumbers() {
        int gen;
        if ("Feminin" == Porqueria.addGender) {
            Porqueria.gen = 2;
        } else {
            Porqueria.gen = 1;
        }
        return Porqueria.gen;
    }

    // calculating the z-score using L, M, S for a maximal age - CDC and WHO standards
    public double getZscoreWithMaximalAge(double addVariable, ArrayList<String> list, int maximalAge) {
        double zscoreH = 0;
        if (addVariable == 0) {
            zscoreH = 0;
        }
        if (addTotalMonths <= maximalAge) {
            for (int i = 1; i < list.size() - 3; i += 5) {
                if (Integer.parseInt(list.get(i)) == addTotalMonths && Integer.parseInt(list.get(i - 1)) == getGenderNumbers()) {
                    double L = Double.parseDouble(list.get(i + 1));
                    double M = Double.parseDouble(list.get(i + 2));
                    double S = Double.parseDouble(list.get(i + 3));
                    zscoreH = (Math.pow((addVariable / M), L) - 1) / (L * S);
                }
            }
        }
        if (addTotalMonths > maximalAge) {
            zscoreH = 0;
        }
        return zscoreH;
    }

    // calculating the z-score using L, M, S - CDC and WHO standards
    public double getZscoreGeneral(double addVariable, ArrayList<String> list) {
        double zscoreH = 0;
        if (addVariable == 0) {
            zscoreH = 0;
        }
        for (int i = 1; i < list.size() - 3; i += 5) {
            if (Integer.parseInt(list.get(i)) == addTotalMonths && Integer.parseInt(list.get(i - 1)) == getGenderNumbers()) {
                double L = Double.parseDouble(list.get(i + 1));
                double M = Double.parseDouble(list.get(i + 2));
                double S = Double.parseDouble(list.get(i + 3));
                zscoreH = (Math.pow((addVariable / M), L) - 1) / (L * S);
            }
        }
        return zscoreH;
    }

    // calculating parameters utilising French standards
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
                if (itemvarsta == addTotalMonths && addTotalMonths > 60) {
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
                if (itemvarsta == addTotalMonths && addTotalMonths > 60) {
                    zscoreHR = (addHeight - itemTalieMedieB) / itemDSB;
                }
            }
        }
        return zscoreHR;
    }

    public double getProcentWeightToHeightRo() {
        double procentWeightToHeightRo = 0;
        String addGender = Porqueria.addGender;
        double addHeight = Porqueria.addHeight;
        double addWeight = Porqueria.addWeight;
        int idx;
        if (addHeight == 0 && addWeight == 0) {
            procentWeightToHeightRo = 0;
        }
        if (addGender.equals("Feminin")) {
            for (int j = 1; j < listHeightRo.size() - 12; j += 7) {
                double talieTabel = Double.parseDouble((listHeightRo.get(j)));
                double talieTabel2 = Double.parseDouble(valueOf(listHeightRo.get(j + 7)));
                double greutateTabel = Double.parseDouble(valueOf(listHeightRo.get(j + 2)));
                double greutateTabel2 = Double.parseDouble(valueOf(listHeightRo.get(j + 9)));
                if (addHeight > talieTabel && addHeight < talieTabel2) {
                    double greutateaCorespTalieiAprox = (addHeight - talieTabel) / ((talieTabel2 - talieTabel) / 6) *
                            ((greutateTabel2 - greutateTabel) / 6) + greutateTabel;
                    procentWeightToHeightRo =
                            ((addWeight - greutateaCorespTalieiAprox) * 100) / greutateaCorespTalieiAprox;
                }
                if (addHeight == talieTabel) {
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
                            greutateTabel + ((addHeight - talieTabel) * ((greutateTabel2 - greutateTabel) / 6) / ((talieTabel2 - talieTabel) / 6));
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
        int idx;
        if (addHeight == 0) {
            procentWeightToHeightRo = 0;
        }
        if ("Feminin" == addGender) {
            for (int j = 1; j < listHeightRo.size() - 12; j += 7) {
                double talieTabel = Double.parseDouble((listHeightRo.get(j)));
                double talieTabel2 = Double.parseDouble(valueOf(listHeightRo.get(j + 7)));
                double greutateTabel = Double.parseDouble(valueOf(listHeightRo.get(j + 2)));
                double greutateTabel2 = Double.parseDouble(valueOf(listHeightRo.get(j + 9)));
                if (addHeight > talieTabel && addHeight < talieTabel2) {
                    procentWeightToHeightRo = (addHeight - talieTabel) / ((talieTabel2 - talieTabel) / 6) *
                            ((greutateTabel2 - greutateTabel) / 6) + greutateTabel;
                }
                if (addHeight == talieTabel) {
                    idx = j;
                    procentWeightToHeightRo = Double.parseDouble(valueOf(listHeightRo.get(idx + 2)));
                }

            }
        }
        if ("Masculin".equals(addGender)) {
            for (int j = 4; j < listHeightRo.size() - 12; j += 7) {
                double talieTabel = Double.parseDouble((listHeightRo.get(j)));
                double talieTabel2 = Double.parseDouble(valueOf(listHeightRo.get(j + 7)));
                double greutateTabel = Double.parseDouble(valueOf(listHeightRo.get(j + 2)));
                double greutateTabel2 = Double.parseDouble(valueOf(listHeightRo.get(j + 9)));
                if (addHeight > talieTabel && addHeight < talieTabel2) {
                    procentWeightToHeightRo = (addHeight - talieTabel) / ((talieTabel2 - talieTabel) / 6) *
                            ((greutateTabel2 - greutateTabel) / 6) + greutateTabel;
                }
                if (addHeight == talieTabel) {
                    idx = j;
                    procentWeightToHeightRo = Double.parseDouble(valueOf(listHeightRo.get(idx + 2)));
                }
            }
        }

        return procentWeightToHeightRo;
    }


    static List<String> listHeightRo = Arrays.asList(
            "1", "52.40", "1.9", "3.75", "53.2", "2", "3.96",
            "2", "55.6", "2", "4.6", "56.70", "2.1", "4.9",
            "3", "58.3", "2", "5.35", "59.9", "2.2", "5.75",
            "4", "61", "2.1", "6", "62.5", "2.2", "6.48",
            "5", "63", "2.2", "6.6", "64.7", "2.2", "7.10",
            "6", "64.8", "2.2", "7.11", "66.4", "2.3", "7.58",
            "7", "66.4", "2.2", "7.6", "68", "2.3", "8.08",
            "8", "67.8", "2.3", "8.05", "69.5", "2.3", "8.5",
            "9", "69.1", "2.3", "8.39", "70.8", "2.3", "8.88",
            "10", "70.3", "2.3", "8.72", "72", "2.3", "9.2",
            "11", "71.5", "2.4", "9", "73.2", "2.4", "9.52",
            "12", "72.6", "2.5", "9.24", "74.3", "2.4", "9.81",
            "13", "73.7", "2.5", "9.48", "75.4", "2.4", "10.08",
            "14", "74.8", "2.6", "9.71", "76.5", "2.5", "10.33",
            "15", "75.9", "2.7", "9.93", "77.6", "2.6", "10.55",
            "16", "77", "2.7", "10.15", "78.5", "2.6", "10.75",
            "17", "78", "2.8", "10.35", "79.6", "2.7", "10.95",
            "18", "79", "2.9", "10.56", "80.5", "2.7", "11.14",
            "19", "79.9", "2.9", "10.74", "81.4", "2.8", "11.31",
            "20", "80.80", "3", "10.91", "82.3", "2.8", "11.48",
            "21", "81.7", "3", "11.08", "83.2", "2.9", "11.65",
            "22", "82.6", "3", "11.25", "84", "2.9", "11.82",
            "23", "83.5", "3.1", "11.42", "84.8", "2.9", "12",
            "24", "84.3", "3.1", "11.59", "85.6", "3", "12.18",
            "25", "85.1", "3.1", "11.77", "86.4", "3", "12.36",
            "26", "85.9", "3.1", "11.95", "87.2", "3", "12.54",
            "27", "86.7", "3.1", "12.13", "88", "3.1", "12.71",
            "28", "87.4", "3.2", "12.30", "88.8", "3.1", "12.88",
            "29", "88.1", "3.2", "12.47", "89.5", "3.2", "13.05",
            "30", "88.8", "3.2", "12.64", "90.2", "3.2", "13.22",
            "31", "89.5", "3.2", "12.8", "90.9", "3.2", "13.38",
            "32", "90.2", "3.2", "12.96", "91.6", "3.3", "13.53",
            "33", "90.90", "3.2", "13.12", "92.3", "3.3", "13.68",
            "34", "91.5", "3.3", "13.28", "93", "3.4", "13.83",
            "35", "92.1", "3.3", "13.44", "93.6", "3.4", "13.98",
            "36", "92.7", "3.3", "13.6", "94.2", "3.5", "14.14",
            "37", "93.4", "3.3", "13.76", "94.9", "3.5", "14.30",
            "38", "94", "3.3", "13.92", "95.5", "3.5", "14.47",
            "39", "94.6", "3.4", "14.07", "96.1", "3.6", "14.64",
            "40", "95.2", "3.4", "14.22", "96.7", "3.6", "14.81",
            "41", "95.8", "3.4", "14.37", "97.3", "3.7", "14.98",
            "42", "96.4", "3.5", "14.52", "97.9", "3.7", "15.15",
            "43", "97", "3.5", "14.66", "98.5", "3.7", "15.30",
            "44", "97.6", "3.5", "14.80", "99.1", "3.7", "15.45",
            "45", "98.2", "3.5", "14.94", "99.7", "3.8", "15.60",
            "46", "98.8", "3.6", "15.08", "100.3", "3.8", "15.74",
            "47", "99.3", "3.6", "15.22", "100.8", "3.8", "15.88",
            "48", "99.8", "3.6", "15.36", "101.3", "3.9", "16.02",
            "49", "100.4", "3.6", "15.52", "101.8", "3.9", "16.17",
            "50", "101", "3.7", "15.68", "102.3", "3.9", "16.32",
            "51", "101.6", "3.7", "15.84", "102.8", "3.9", "16.47",
            "52", "102.1", "3.7", "16", "103.3", "4", "16.63",
            "53", "102.6", "3.8", "16.16", "103.8", "4", "16.79",
            "54", "103.1", "3.8", "16.32", "104.5", "4", "16.95",
            "55", "103.7", "3.8", "16.47", "105.1", "4", "17.1",
            "56", "104.3", "3.9", "16.62", "105.7", "4.1", "17.25",
            "57", "105.3", "3.9", "16.77", "106.2", "4.1", "17.4",
            "58", "105.8", "3.9", "16.92", "106.8", "4.2", "17.55",
            "59", "105.8", "4", "17.07", "107.2", "4.2", "17.71",
            "60", "106.3", "4", "17.22", "107.7", "4.2", "17.87",
            "66", "109.3", "4.1", "18.06", "110.8", "4.4", "18.90",
            "72", "112.2", "4.2", "19.02", "113.80", "4.5", "19.87",
            "78", "115.2", "4.4", "20.11", "116.80", "4.7", "21.03",
            "84", "118.2", "4.6", "21.26", "119.7", "4.9", "22.23",
            "90", "121.1", "4.7", "22.48", "122.5", "5", "23.44",
            "96", "123.9", "4.8", "23.83", "125.3", "5.1", "24.73",
            "102", "126.7", "4.9", "25.24", "128", "5.2", "26.09",
            "108", "129.4", "5", "26.66", "130.6", "5.3", "27.47",
            "114", "132.1", "5.1", "28.10", "133.1", "5.3", "28.84",
            "120", "134.7", "5.3", "29.62", "135.6", "5.3", "30.34",
            "126", "137.7", "5.4", "31.44", "138.1", "5.5", "31.81",
            "132", "140.8", "5.7", "33.41", "140.6", "5.7", "33.34",
            "138", "144.2", "5.9", "35.53", "143.2", "5.9", "35.18",
            "144", "147.7", "6.2", "37.91", "145.9", "6.2", "37.02",
            "150", "151.2", "6.1", "40.87", "149.2", "6.5", "39.48",
            "156", "154.3", "6", "43.53", "152.5", "7.1", "42.02",
            "162", "156.7", "5.8", "45.68", "156.3", "7.8", "45.2",
            "168", "158.5", "6.7", "47.66", "160", "7.9", "48.2",
            "174", "159.9", "5.6", "49.4", "163.3", "8", "51.1",
            "180", "160.9", "5.5", "50.6", "166.4", "7.5", "54",
            "186", "161.5", "5.5", "51.6", "169.1", "6.8", "56.4",
            "192", "162", "5.5", "52.1", "171", "6.2", "58.4",
            "198", "162.4", "5.5", "52.6", "172.4", "6", "60",
            "204", "162.6", "5.5", "53", "173.3", "5.9", "61.2");

    static final class DateSelector {
        private int yearsCalendar;
        private int monthsCalendar;

        public DateSelector(int yearsCalendar, int monthsCalendar) {
            this.yearsCalendar = yearsCalendar;
            this.monthsCalendar = monthsCalendar;
        }
        public int getYearsCalendar() {
            return yearsCalendar;
        }
        public int getMonthsCalendar() {
            return monthsCalendar;
        }
    }

    public DateSelector getDateFromCalendar(){
        int yearsCalendar = 0, monthsCalendar=0;
        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("dd/MM/yyyy");
        // converting the inputted date to string
        String sDate = birthdayresultGlobal;
        if (sDate == null){sDate = "00-00-0000";}
        String eDate = simpleDateFormat1.format(Calendar.getInstance().getTime());

        // converting it to date format
        Date date1 = null;
        try {
            date1 = simpleDateFormat1.parse(sDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date date2 = null;
        try {
            date2 = simpleDateFormat1.parse(eDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long startdate;
        long endDate;
        assert date2 != null;
        endDate = date2.getTime();
        if (date1 == null) {
            startdate = endDate;
        } else startdate = date1.getTime();

        // condition
        if (startdate <= endDate) {
            org.joda.time.Period period = new Period(startdate, endDate, PeriodType.yearMonthDay());
            yearsCalendar = period.getYears();
            monthsCalendar = period.getMonths();
        }
        return new DateSelector(yearsCalendar, monthsCalendar);
    }

}

