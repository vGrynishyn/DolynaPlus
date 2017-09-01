package ua.com.dolina_plus.db_test;

import android.app.TimePickerDialog;
import android.content.Intent;
import java.util.GregorianCalendar;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import java.util.Calendar;

import static ua.com.dolina_plus.db_test.R.id.rbSecond;


public class SearchFragment extends DialogFragment {

    int DIALOG_TIME = 1;
    private Window SearchFragmentWindow;
    View SearchView;
    String[] CityData = {"Вінниця", "Тернопіль", "Чернівці"};
    private Button btnOK;
    RadioGroup rgDay;
    RadioButton rbFirst, rbSec;

    Spinner spinner;
    TextView tvCurrTime;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        SearchView = inflater.inflate(R.layout.fragment_search, null);
        btnOK = (Button) SearchView.findViewById(R.id.OK);
        rgDay = (RadioGroup)  SearchView.findViewById(R.id.rgDay);
        rbFirst = (RadioButton)  SearchView.findViewById(R.id.rbFirst);
        rbSec = (RadioButton)  SearchView.findViewById(rbSecond);
        spinner = (Spinner) SearchView.findViewById(R.id.spinner);
        tvCurrTime = (TextView) SearchView.findViewById(R.id.CurTime);

        setCurrentDay();
        setCurrentTime();

        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String city = spinner.getSelectedItem().toString();
                String Time = tvCurrTime.getText().toString();
                String NewTime = Time.substring(0,2)+":00";
                int dayType=1;
                switch (rgDay.getCheckedRadioButtonId()) {
                    case R.id.rbFirst:
                        dayType = 1;
                        break;
                    case rbSecond:
                        dayType = 2;
                        break;
                }

                Intent intent = new Intent(getContext(), SearchResultActivity.class);
                intent.putExtra("city", city);
                intent.putExtra("Time", NewTime);
                intent.putExtra("dayType", dayType);
               // intent.putExtra("endTime", EndTime);
                startActivity(intent);

                getDialog().dismiss();
            }
        });

        tvCurrTime.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(getActivity(), android.R.style.Theme_Holo_Light_Dialog_NoActionBar,new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        String strHour = String.valueOf(selectedHour);
                        String strMinutes = String.valueOf(selectedMinute);
                        if(selectedHour<10) {
                            strHour = "0"+strHour;        }
                        if(selectedMinute<10){
                            strMinutes = "0"+strMinutes;  }
                        tvCurrTime.setText(strHour + ":" + strMinutes);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, CityData);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerArrayAdapter);
        spinner.setPrompt("Місто");

        // put window to top of screen
        SearchFragmentWindow = getDialog().getWindow();
        SearchFragmentWindow.setGravity(Gravity.TOP);

        //put up window a bit above
        WindowManager.LayoutParams params = SearchFragmentWindow.getAttributes();
        params.x = 0;
        params.y = (int) getResources().getDimension(R.dimen.window_bottom_margin);
        SearchFragmentWindow.setAttributes(params);
        return SearchView;
    }
    public String SetCorrectTime(int time){
        String NewTime = String.valueOf(time);
        if(time<10)
            NewTime = "0"+String.valueOf(time);

        return NewTime;
    }
    void setCurrentTime(){
        String StrHour, StrMinutes;
        Calendar mTime = Calendar.getInstance();
        StrHour = String.valueOf(mTime.get(Calendar.HOUR_OF_DAY));
        StrMinutes = String.valueOf(mTime.get(Calendar.MINUTE));
        if(Integer.parseInt(StrHour)<10) {
            StrHour = "0" + StrHour;
        }
        if(Integer.parseInt(StrMinutes)<10) {
            StrMinutes = "0" + StrMinutes;
        }
        tvCurrTime.setText(StrHour +":"+StrMinutes);
    }

    void setCurrentDay(){
        int dayOfWeek  = 7 - (8 - new GregorianCalendar().get(Calendar.DAY_OF_WEEK))%7;
        if (dayOfWeek==1 || dayOfWeek==3 || dayOfWeek==5)
            rbFirst.setChecked(true);
        else if(dayOfWeek==2 || dayOfWeek==4 || dayOfWeek==6)
            rbSec.setChecked(true);
     }
}
