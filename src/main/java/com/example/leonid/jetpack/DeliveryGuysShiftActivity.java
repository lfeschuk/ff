package com.example.leonid.jetpack;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import Objects.DeliveryGuysShift;

import static Objects.DeliveryGuysShift.KindShift.DOUBLE;
import static Objects.DeliveryGuysShift.KindShift.EVENING;
import static Objects.DeliveryGuysShift.KindShift.MORNING;
import static Objects.DeliveryGuysShift.KindShift.NONE;


public class DeliveryGuysShiftActivity extends AppCompatActivity {
    public static final String TAG = "DeliveryGuysShiftActiv";
 //   public enum Days {SUNDAY,MONDAY,TUESDAY,WEDNESDAY,THURSDAY,FRIDAY,SATURDAY};
    public static final int SUNDAY  = 0;
    public static final int MONDAY  = 1;
    public static final int TUESDAY  = 2;
    public static final int WEDNESDAY  = 3;
    public static final int THURSDAY  = 4;
    public static final int FRIDAY  = 5;
    public static final int SATURDAY  = 6;
    public static final int NAME  = 7;
    Button send_to_guy_button;


    Button send;
    private  Toolbar toolbar;
    TableLayout main_table;
    TableLayout second_table;
    int[][] wanted_array  = new int[2][7];
    int[][] given_array  = new int[2][7];
    int[][] missing_array  = new int[2][7];
    TextView[][] given_Array_textView = new TextView[2][7];
    TextView[][] missing_Array_textView = new TextView[2][7];
    ArrayList<DeliveryGuysShift> array_shift_toDB = new ArrayList<>();
    ArrayList<DeliveryGuysShift> array_shift_original = new ArrayList<>();
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG,"on create start");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.delivery_guys_shifts_activity);
        Log.d(TAG,"on intent");
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        main_table = findViewById(R.id.tableLayout_dev_guy_shift);
        second_table = findViewById(R.id.tableLayout_shift_status);
        set_wanted_table();
        set_given_table();
        set_missing_table();
        get_shift_query();

         send = findViewById(R.id.send);
        send_to_guy_button = findViewById(R.id.send_to_guy);






        TextView title = findViewById(R.id.main_title_text);
        title.setText("משמרות שליחים");
        Log.d(TAG,"after intent");

    }

    public void set_missing_table()
    {
        missing_Array_textView[0][0] = second_table.findViewById(R.id.sunday_morning_missing);

    Log.d(TAG,"set_missing_table " +  missing_Array_textView[0][0] );

        missing_Array_textView[1][0] =  second_table.findViewById(R.id.sunday_evening_missing);


        missing_Array_textView[0][1] =  second_table.findViewById(R.id. monday_morning_missing);


        missing_Array_textView[1][1] = second_table.findViewById(R.id. monday_evening_missing);


        missing_Array_textView[0][2] =  second_table.findViewById(R.id.tuesday_morning_missing);


        missing_Array_textView[1][2] =  second_table.findViewById(R.id.tuesday_evening_missing);

        missing_Array_textView[0][3] =  second_table.findViewById(R.id.wednesday_morning_missing);



        missing_Array_textView[1][3] =  second_table.findViewById(R.id.wednesday_evening_missing);

        missing_Array_textView[0][4] =  second_table.findViewById(R.id.thursday_morning_missing);



        missing_Array_textView[1][4] = second_table.findViewById(R.id.thursday_evening_missing);


        missing_Array_textView[0][5] = second_table.findViewById(R.id.friday_morning_missing);




        missing_Array_textView[1][5] =  second_table.findViewById(R.id.friday_evening_missing);;



        missing_Array_textView[0][6] =  second_table.findViewById(R.id.saturday_morning_missing);;


        missing_Array_textView[1][6] =  second_table.findViewById(R.id.saturday_evening_missing);
    }
    public void set_given_table()
    {
        TextView sunday_morning_given = second_table.findViewById(R.id.sunday_morning_given);
        given_Array_textView[0][0] = sunday_morning_given;

        TextView sunday_evening_given = second_table.findViewById(R.id.sunday_evening_given);
        given_Array_textView[1][0] = sunday_evening_given;

        TextView monday_morning_given = second_table.findViewById(R.id. monday_morning_given);
        given_Array_textView[0][1] = monday_morning_given;

        TextView  monday_evening_given = second_table.findViewById(R.id. monday_evening_given);
        given_Array_textView[1][1] = monday_evening_given;

        TextView tuesday_morning_given = second_table.findViewById(R.id.tuesday_morning_given);
        given_Array_textView[0][2] = tuesday_morning_given;

        TextView tuesday_evening_given = second_table.findViewById(R.id.tuesday_evening_given);
        given_Array_textView[1][2] = tuesday_evening_given;

        TextView wednesday_morning_given = second_table.findViewById(R.id.wednesday_morning_given);
        given_Array_textView[0][3] = wednesday_morning_given;


        TextView wednesday_evening_given = second_table.findViewById(R.id.wednesday_evening_given);
        given_Array_textView[1][3] = wednesday_evening_given;

        TextView thursday_morning_given = second_table.findViewById(R.id.thursday_morning_given);
        given_Array_textView[0][4] = thursday_morning_given;

        TextView thursday_evening_given = second_table.findViewById(R.id.thursday_evening_given);
        given_Array_textView[1][4] = thursday_evening_given;

        TextView friday_morning_given = second_table.findViewById(R.id.friday_morning_given);
        given_Array_textView[0][5] = friday_morning_given;



        TextView friday_evening_given = second_table.findViewById(R.id.friday_evening_given);
        given_Array_textView[1][5] = friday_evening_given;


        TextView saturday_morning_given = second_table.findViewById(R.id.saturday_morning_given);
        given_Array_textView[0][6] = saturday_morning_given;

        TextView saturday_evening_given = second_table.findViewById(R.id.saturday_evening_given);
        given_Array_textView[1][6] = saturday_evening_given;


    }
    public void set_wanted_table()
    {
        for (int i = 0; i<2;i++)
        {
            for (int j=0;j<7;j++)
            {
                wanted_array[i][j] = 3;
            }
        }
        ArrayList<String> options =  new ArrayList<>();
        options.add("0");
        options.add("1");
        options.add("2");
        options.add("3");
        options.add("4");
        options.add("5");
        options.add("6");
        options.add("7");
        options.add("8");
        options.add("9");
        options.add("10");
        options.add("11");
        options.add("12");
        options.add("13");
        options.add("14");
        options.add("15");
        ArrayAdapter adapter = new ArrayAdapter<>(this, R.layout.spinner_item,options );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        AppCompatSpinner sunday_morning_wanted = second_table.findViewById(R.id.sunday_morning_wanted);
        sunday_morning_wanted.setAdapter(adapter);
        setSpinnerWanted(sunday_morning_wanted,0,0);

        AppCompatSpinner sunday_evening_wanted = second_table.findViewById(R.id.sunday_evening_wanted);
        sunday_evening_wanted.setAdapter(adapter);
        setSpinnerWanted(sunday_evening_wanted,1,0);

        AppCompatSpinner monday_morning_wanted = second_table.findViewById(R.id. monday_morning_wanted);
        monday_morning_wanted.setAdapter(adapter);
        setSpinnerWanted(monday_morning_wanted,0,1);

        AppCompatSpinner  monday_evening_wanted = second_table.findViewById(R.id. monday_evening_wanted);
        monday_evening_wanted.setAdapter(adapter);
        setSpinnerWanted(monday_evening_wanted,1,1);

        AppCompatSpinner tuesday_morning_wanted = second_table.findViewById(R.id.tuesday_morning_wanted);
        tuesday_morning_wanted.setAdapter(adapter);
        setSpinnerWanted(tuesday_morning_wanted,0,2);

        AppCompatSpinner tuesday_evening_wanted = second_table.findViewById(R.id.tuesday_evening_wanted);
        tuesday_evening_wanted.setAdapter(adapter);
        setSpinnerWanted(tuesday_evening_wanted,1,2);

        AppCompatSpinner wednesday_morning_wanted = second_table.findViewById(R.id.wednesday_morning_wanted);
        wednesday_morning_wanted.setAdapter(adapter);
        setSpinnerWanted(wednesday_morning_wanted,0,3);

        AppCompatSpinner wednesday_evening_wanted = second_table.findViewById(R.id.wednesday_evening_wanted);
        wednesday_evening_wanted.setAdapter(adapter);
        setSpinnerWanted(wednesday_evening_wanted,1,3);

        AppCompatSpinner thursday_morning_wanted = second_table.findViewById(R.id.thursday_morning_wanted);
        thursday_morning_wanted.setAdapter(adapter);
        setSpinnerWanted(thursday_morning_wanted,0,4);

        AppCompatSpinner thursday_evening_wanted = second_table.findViewById(R.id.thursday_evening_wanted);
        thursday_evening_wanted.setAdapter(adapter);
        setSpinnerWanted(thursday_evening_wanted,1,4);

        AppCompatSpinner friday_morning_wanted = second_table.findViewById(R.id.friday_morning_wanted);
        friday_morning_wanted.setAdapter(adapter);
        setSpinnerWanted(friday_morning_wanted,0,5);


        AppCompatSpinner friday_evening_wanted = second_table.findViewById(R.id.friday_evening_wanted);
        friday_evening_wanted.setAdapter(adapter);
        setSpinnerWanted(friday_evening_wanted,1,5);

        AppCompatSpinner saturday_morning_wanted = second_table.findViewById(R.id.saturday_morning_wanted);
        saturday_morning_wanted.setAdapter(adapter);
        setSpinnerWanted(saturday_morning_wanted,0,6);

        AppCompatSpinner saturday_evening_wanted = second_table.findViewById(R.id.saturday_evening_wanted);
        saturday_evening_wanted.setAdapter(adapter);
        setSpinnerWanted(saturday_evening_wanted,1,6);


    }
    //i means day/evening(0 is morning)   j means day of the weeek
    public void setSpinnerWanted(AppCompatSpinner spinner,final int i,final int j)
    {
        spinner.setSelection(2);
        spinner.setGravity(Gravity.CENTER_HORIZONTAL);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int index_pressed, long lon) {
//
                wanted_array[i][j] = index_pressed;
                setMissing_by_index_value(i,j);
//                String companyId = companies_list.get(i).getCompanyId();
//                String companyName = companies_list.get(i).getCompanyName();
//                Toast.makeText(MainActivity.this, "Company Name: " + companyName, Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
    public void on_name_click(final TextView name,final DeliveryGuysShift shift , final Context context)
    {
        name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG,"on click");
                AlertDialog.Builder builder =  new AlertDialog.Builder(context);
                LayoutInflater li = LayoutInflater.from(context);
                final View myView = li.inflate(R.layout.on_shift_click_dialog, null);
            //    final Dialog dialog = new Dialog(context,R.style.Dialog);


//                dialog.setContentView(R.layout.on_shift_click_dialog);
//                dialog.setTitle(shift.getName());
                TextView text_1 = (TextView) myView.findViewById(R.id.sunday_shift);
                text_1.setText("ראשון:" + DeliveryGuysShift.KindShift.valueOf(shift.getSunday()));
                TextView text_2 = (TextView) myView.findViewById(R.id.monday_shift);
                text_2.setText("שני:" + DeliveryGuysShift.KindShift.valueOf(shift.getMonday()));
                TextView text_3 = (TextView) myView.findViewById(R.id.tuesday_shift);
                text_3.setText("שלישי:" + DeliveryGuysShift.KindShift.valueOf(shift.getTuesday()));
                TextView text_4 = (TextView) myView.findViewById(R.id.wednesday_shift);
                text_4.setText("רביעי:" + DeliveryGuysShift.KindShift.valueOf(shift.getWednesday()));
                TextView text_5 = (TextView) myView.findViewById(R.id.thursday_shift);
                text_5.setText("חמישי:" + DeliveryGuysShift.KindShift.valueOf(shift.getThursday()));
                TextView text_6 = (TextView) myView.findViewById(R.id.friday_shift);
                text_6.setText("שישי:" + DeliveryGuysShift.KindShift.valueOf(shift.getFriday()));
                TextView text_7 = (TextView) myView.findViewById(R.id.saturday_shift);
                text_7.setText("שבת:" + DeliveryGuysShift.KindShift.valueOf(shift.getSaturday()));
                TextView text_8 = (TextView) myView.findViewById(R.id.total_morning_shift);
                text_8.setText("משמרות בוקר:" + shift.getTotal_morning());
                TextView text_9 = (TextView) myView.findViewById(R.id.total_evening_shift);
                text_9.setText("משמרות ערב:" + shift.getTotal_evening());
                TextView text_10 = (TextView) myView.findViewById(R.id.total_shift);
                text_10.setText("סה''כ משמרות:" + shift.getTotal_evening() + shift.getTotal_morning());

                builder.setView(myView);
                builder.setTitle(shift.getName());
                builder.setNeutralButton("סיום", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                final  AlertDialog dialog = builder.create();

                dialog.show();

            }
        });
    }
    public TableRow create_row()
    {
        TableRow row= new TableRow(this);
        TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,TableRow.LayoutParams.WRAP_CONTENT);
        row.setLayoutParams(lp);
        TextView name = new TextView(this);
        name.setId(NAME);
        AppCompatSpinner sunday_sp = new AppCompatSpinner(this);
        sunday_sp.setId(SUNDAY);
        sunday_sp.setGravity(Gravity.CENTER_HORIZONTAL);
        AppCompatSpinner monday_sp = new AppCompatSpinner(this);
        monday_sp.setId(MONDAY);
        monday_sp.setGravity(Gravity.CENTER_HORIZONTAL);
        AppCompatSpinner tuesday_sp = new AppCompatSpinner(this);
        tuesday_sp.setId(TUESDAY);
        tuesday_sp.setGravity(Gravity.CENTER_HORIZONTAL);
        AppCompatSpinner wednesday_sp = new AppCompatSpinner(this);
        wednesday_sp.setId(WEDNESDAY);
        wednesday_sp.setGravity(Gravity.CENTER_HORIZONTAL);
        AppCompatSpinner thursday_sp = new AppCompatSpinner(this);
        thursday_sp.setId(THURSDAY);
        thursday_sp.setGravity(Gravity.CENTER_HORIZONTAL);
        AppCompatSpinner friday_sp = new AppCompatSpinner(this);
        friday_sp.setId(FRIDAY);
        friday_sp.setGravity(Gravity.CENTER_HORIZONTAL);
        AppCompatSpinner saturday_sp = new AppCompatSpinner(this);
        saturday_sp.setId(SATURDAY);
        saturday_sp.setGravity(Gravity.CENTER_HORIZONTAL);
        row.addView(name);
        row.addView(sunday_sp);
        row.addView(monday_sp);
        row.addView(tuesday_sp);
        row.addView(wednesday_sp);
        row.addView(thursday_sp);
        row.addView(friday_sp);
        row.addView(saturday_sp);

        return row;


    }
    public void set_row(TableRow row,DeliveryGuysShift shift,final int index_shift)
    {
        Log.d(TAG,"set_row +row: " +row + " shift: " +shift.getName() );
        ArrayList<String> options =  new ArrayList<>();
        options.add("-");
        options.add("בוקר");
        options.add("ערב");
        options.add("כפולה");
        ArrayAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,options );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        TextView name = row.findViewById(NAME);
        name.setTypeface(null, Typeface.BOLD);
        name.setText(shift.getName());
            on_name_click(name, shift,this);
        AppCompatSpinner sunday_sp = row.findViewById(SUNDAY);
        sunday_sp.setAdapter(adapter);
        set_spiner(sunday_sp,shift,SUNDAY);

        AppCompatSpinner monday_sp = row.findViewById(MONDAY);
        monday_sp.setAdapter(adapter);
        set_spiner(monday_sp,shift,MONDAY);
        AppCompatSpinner tuesday_sp = row.findViewById(TUESDAY);
        tuesday_sp.setAdapter(adapter);
        set_spiner(tuesday_sp,shift,TUESDAY);
        AppCompatSpinner wednesday_sp = row.findViewById(WEDNESDAY);
        wednesday_sp.setAdapter(adapter);
        set_spiner(wednesday_sp,shift,WEDNESDAY);
        AppCompatSpinner thursday_sp = row.findViewById(THURSDAY);
        thursday_sp.setAdapter(adapter);
        set_spiner(thursday_sp,shift,THURSDAY);
        AppCompatSpinner friday_sp = row.findViewById(FRIDAY);
        friday_sp.setAdapter(adapter);
        set_spiner(friday_sp,shift,FRIDAY);
        AppCompatSpinner saturday_sp = row.findViewById(SATURDAY);
        saturday_sp.setAdapter(adapter);
        set_spiner(saturday_sp,shift,SATURDAY);


    }
    public void set_spiner(AppCompatSpinner spinner,final DeliveryGuysShift shift,final int day /*start from 0 to 6*/)
    {
        Log.d(TAG,"set_spiner + " + spinner + " shift: " +shift.getName() + " day: " +day);
        int selection;
        switch (day)
        {
            case SUNDAY:
                selection = shift.getSunday().ordinal();
                break;
            case MONDAY:
                selection = shift.getMonday().ordinal();
                break;
            case TUESDAY:
                selection = shift.getTuesday().ordinal();
                break;
            case WEDNESDAY:
                selection = shift.getWednesday().ordinal();
                break;
            case THURSDAY:
                selection = shift.getThursday().ordinal();
                break;
            case FRIDAY:
                selection = shift.getFriday().ordinal();
                break;
            case SATURDAY:
                selection = shift.getSaturday().ordinal();
                break;
            default:
                selection = 0;
        }
        spinner.setSelection(selection);
        spinner.setGravity(Gravity.CENTER_HORIZONTAL);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                int prev = 0;
                switch (day)
                {
                    case SUNDAY:
                        prev = shift.getSunday().ordinal();
                        break;
                    case MONDAY:
                        prev = shift.getMonday().ordinal();
                        break;
                    case TUESDAY:
                        prev = shift.getThursday().ordinal();
                        break;
                    case WEDNESDAY:
                        prev = shift.getWednesday().ordinal();
                        break;
                    case THURSDAY:
                        prev = shift.getThursday().ordinal();
                        break;
                    case FRIDAY:
                        prev = shift.getFriday().ordinal();
                        break;
                    case SATURDAY:
                        prev = shift.getSaturday().ordinal();
                        break;
                    default:
                       break;
                }
                switch (i)
                {//none
                    case 0:
                        if (prev == MORNING.ordinal())
                        {
                            given_array[0][day] -= 1;
                        }
                        else if (prev == EVENING.ordinal())
                        {
                            given_array[1][day] -= 1;
                        }
                        else if (prev == DOUBLE.ordinal())
                        {
                            given_array[0][day] -= 1;
                            given_array[1][day] -= 1;
                        }
                        break;
                    //morning
                    case 1:
                        if (prev == NONE.ordinal())
                        {
                            given_array[0][day] += 1;
                        }
                        else if (prev == EVENING.ordinal())
                        {
                            given_array[1][day] -= 1;
                            given_array[0][day] += 1;
                        }
                        else if (prev == DOUBLE.ordinal())
                        {
                            given_array[1][day] -= 1;
                        }
                        break;
                    //evening
                    case 2:
                        if (prev == NONE.ordinal())
                        {
                            given_array[1][day] += 1;
                        }
                        else if (prev == MORNING.ordinal())
                        {
                            given_array[1][day] += 1;
                            given_array[0][day] -= 1;
                        }
                        else if (prev == DOUBLE.ordinal())
                        {
                            given_array[0][day] -= 1;
                        }
                        break;
                    //double
                    case 3:
                        if (prev == NONE.ordinal())
                        {
                            given_array[1][day] += 1;
                            given_array[0][day] += 1;
                        }
                        else if (prev == MORNING.ordinal())
                        {
                            given_array[1][day] += 1;
                        }
                        else if (prev == EVENING.ordinal())
                        {
                            given_array[0][day] += 1;
                        }
                        break;

                }
                //morning and evening
                setMissing_by_index_value(0,day);
                setMissing_by_index_value(1,day);
                switch (day)
                {
                    case SUNDAY:
                        shift.setSunday(DeliveryGuysShift.KindShift.valueOf(i));
                        break;
                    case MONDAY:
                        shift.setMonday(DeliveryGuysShift.KindShift.valueOf(i));
                        break;
                    case TUESDAY:
                        shift.setTuesday(DeliveryGuysShift.KindShift.valueOf(i));
                        break;
                    case WEDNESDAY:
                        shift.setWednesday(DeliveryGuysShift.KindShift.valueOf(i));
                        break;
                    case THURSDAY:
                        shift.setThursday(DeliveryGuysShift.KindShift.valueOf(i));
                        break;
                    case FRIDAY:
                        shift.setFriday(DeliveryGuysShift.KindShift.valueOf(i));
                        break;
                    case SATURDAY:
                        shift.setSaturday(DeliveryGuysShift.KindShift.valueOf(i));
                        break;
                    default:
                        break;
                }


            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }
    public void get_shift_query()
    {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
        c.set(Calendar.DAY_OF_WEEK,Calendar.SUNDAY);
        //next sunday
        c.add(Calendar.DATE,7);
        //last sunday/today
       final String sunday =  df.format(c.getTime());
       Log.d(TAG,"sunday date is: " + sunday);
        Query mDatabase =  FirebaseDatabase.getInstance().getReference("Delivery_Guys_Shifts");
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
              //  Log.d(TAG,"td: " + td.values());
                for(DataSnapshot ds : dataSnapshot.getChildren())
                {
                   for (DataSnapshot ds2: ds.getChildren())
                   {
                       DeliveryGuysShift temp = new DeliveryGuysShift(ds2.getValue(DeliveryGuysShift.class));
                       Log.d(TAG,"temp: " + temp + " name " + temp.getName() + " date " + temp.getDate() + " datesun " + temp.getDate_of_sunday());
                       if (temp.getDate_of_sunday().equals(sunday)) {
                           //get it from approved db
                           if (temp.getIs_approved())
                           {
                              Query mDatabase =  FirebaseDatabase.getInstance().getReference("Delivery_Guys_Shifts_Aproved");
                              mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                                  @Override
                                  public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                      for(DataSnapshot ds : dataSnapshot.getChildren()) {
                                          for (DataSnapshot ds2 : ds.getChildren()) {
                                              DeliveryGuysShift temp = new DeliveryGuysShift(ds2.getValue(DeliveryGuysShift.class));
                                              Log.d(TAG, "temp: " + temp + " name " + temp.getName() + " date " + temp.getDate() + " datesun " + temp.getDate_of_sunday());
                                              if (temp.getDate_of_sunday().equals(sunday)) {
                                                  array_shift_toDB.add(temp);
                                              }
                                          }
                                      }
                                  }

                                  @Override
                                  public void onCancelled(@NonNull DatabaseError databaseError) {

                                  }
                              });
                           }
                           else
                           {
                               array_shift_toDB.add(temp);
                           }

                           array_shift_original.add(temp);
                           //  array_shift_original.add(temp);
                       }
                   }

                }
                set_UI();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }
    public void updateGivenArray(DeliveryGuysShift d)
    {
        if (d.getSunday().equals(MORNING) )
        {
            given_array[0][0]++;
        }
        else if (d.getSunday().equals(DeliveryGuysShift.KindShift.EVENING))
        {
            given_array[1][0]++;
        }
        else if (d.getSunday().equals(DeliveryGuysShift.KindShift.DOUBLE))
        {
            given_array[0][0]++;
            given_array[1][0]++;
        }

        if (d.getMonday().equals(MORNING) )
        {
            given_array[0][1]++;
        }
        else if (d.getMonday().equals(DeliveryGuysShift.KindShift.EVENING))
        {
            given_array[1][1]++;
        }
        else if (d.getMonday().equals(DeliveryGuysShift.KindShift.DOUBLE))
        {
            given_array[0][1]++;
            given_array[1][1]++;
        }

        if (d.getTuesday().equals(MORNING) )
        {
            given_array[0][2]++;
        }
        else if (d.getTuesday().equals(DeliveryGuysShift.KindShift.EVENING))
        {
            given_array[1][2]++;
        }
        else if (d.getTuesday().equals(DeliveryGuysShift.KindShift.DOUBLE))
        {
            given_array[0][2]++;
            given_array[1][2]++;
        }

        if (d.getWednesday().equals(MORNING) )
        {
            given_array[0][3]++;
        }
        else if (d.getWednesday().equals(DeliveryGuysShift.KindShift.EVENING))
        {
            given_array[1][3]++;
        }
        else if (d.getWednesday().equals(DeliveryGuysShift.KindShift.DOUBLE))
        {
            given_array[0][3]++;
            given_array[1][3]++;
        }

        if (d.getThursday().equals(MORNING) )
        {
            given_array[0][4]++;
        }
        else if (d.getThursday().equals(DeliveryGuysShift.KindShift.EVENING))
        {
            given_array[1][4]++;
        }
        else if (d.getThursday().equals(DeliveryGuysShift.KindShift.DOUBLE))
        {
            given_array[0][4]++;
            given_array[1][4]++;
        }


        if (d.getFriday().equals(MORNING) )
        {
            given_array[0][5]++;
        }
        else if (d.getFriday().equals(DeliveryGuysShift.KindShift.EVENING))
        {
            given_array[1][5]++;
        }
        else if (d.getFriday().equals(DeliveryGuysShift.KindShift.DOUBLE))
        {
            given_array[0][5]++;
            given_array[1][5]++;
        }

        if (d.getSaturday().equals(MORNING) )
        {
            given_array[0][6]++;
        }
        else if (d.getSaturday().equals(DeliveryGuysShift.KindShift.EVENING))
        {
            given_array[1][6]++;
        }
        else if (d.getSaturday().equals(DeliveryGuysShift.KindShift.DOUBLE))
        {
            given_array[0][6]++;
            given_array[1][6]++;
        }

    }
    public void setMissing_by_index_value(int i,int j)
    {
        missing_array[i][j] = wanted_array[i][j]-given_array[i][j];
        missing_Array_textView[i][j].setText(String.valueOf( missing_array[i][j]));
    }
    public void setMissing_array()
    {
        for (int i=0;i<2;i++)
        {
            for(int j=0;j<7;j++)
            {
                Log.d(TAG,"setMissing_array i: " +i+" j: " +j);
                missing_array[i][j] = wanted_array[i][j]-given_array[i][j];
                Log.d(TAG,"setMissing_array " +  missing_Array_textView[0][0] );
                Log.d(TAG,"setMissing_array " +  missing_Array_textView[i][j] );
                missing_Array_textView[i][j].setText( String.valueOf( missing_array[i][j]));
            }
        }
    }
    public void set_given_array()
    {
        for (int i=0;i<2;i++)
        {
            for(int j=0;j<7;j++)
            {
                given_Array_textView[i][j].setText( String.valueOf( given_array[i][j]));
            }
        }
    }
    public void set_UI()
    {
        Log.d(TAG,"gg");
        for( int i=0;i<array_shift_toDB.size();i++)
        {
            DeliveryGuysShift d = array_shift_toDB.get(i);
            Log.d(TAG,"set_UI shift: " + d.getName());
            TableRow row = create_row();
            set_row(row,d,i);
            main_table.addView(row);
            updateGivenArray(d);


        }
        setMissing_array();
        set_given_array();
        send_to_guy_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu menu = new PopupMenu(DeliveryGuysShiftActivity.this, view);
                for( DeliveryGuysShift d : array_shift_toDB)
                {
                    menu.getMenu().add(d.getName());
                }
                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                    //    Toast.makeText(DeliveryGuysShiftActivity.this, "pressed on: " + array_shift_toDB.get(item.getItemId()), Toast.LENGTH_SHORT).show();
                        send_shift(item.getItemId());
                       // clicked.set
                        return false;
                    }
                });
                menu.show();

            }
        });
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(int i=0;i<array_shift_toDB.size();i++)
                {
                    send_shift(i);
                }
            }
        });

    }

    public void send_shift(int index)
    {
        DeliveryGuysShift clicked = array_shift_toDB.get(index);
        clicked.setIs_approved(true);
        DatabaseReference mDatabase =  FirebaseDatabase.getInstance().getReference("Delivery_Guys_Shifts_Aproved");
        mDatabase.child(clicked.getIndexString()).child(clicked.getDate_of_sunday()).setValue(clicked);

    }



}
