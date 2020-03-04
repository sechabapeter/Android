package com.example.sechaba.groupl.Classes;


import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.example.sechaba.groupl.R;
import com.example.sechaba.groupl.Show_learner_data;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AppWidgets extends AppWidgetProvider {
    String[] names = new String[100];
    List<LearnerData> learnerList;
    int randomValue, arrayLength;
    Random generator;
    String learnerName, className;

    @Override
    public void onUpdate(final Context context, final AppWidgetManager appWidgetManager, final int[] appWidgetIds) {
        for (final int appWidgetId : appWidgetIds) {

            learnerList = new ArrayList<>();
            Backendless.Persistence.of(LearnerData.class).find(new AsyncCallback<List<LearnerData>>() {
                @Override
                public void handleResponse(List<LearnerData> learners) {

                    arrayLength = learners.size();
                    generator = new Random();
                    randomValue = generator.nextInt(arrayLength);
                    learnerName = "Learner name: " + learners.get(randomValue).getName().toString() + " " + learners.get(randomValue).getLastname().toString();
                    className = "Class name: " + learners.get(randomValue).getClassname().toString();

                    RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widgets);
                    views.setTextViewText(R.id.widget_name, learnerName);
                    views.setTextViewText(R.id.defname, className);

                    Intent intent = new Intent(context, AppWidgets.class);
                    intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
                    intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);

                    PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent,
                            PendingIntent.FLAG_UPDATE_CURRENT);
                    views.setOnClickPendingIntent(R.id.imagewidet, pendingIntent);

                    views.setOnClickPendingIntent(R.id.imagewidet, pendingIntent);
                    Intent intent1 = new Intent(context, Show_learner_data.class);
                    intent1.putExtra("name", learnerList.get(randomValue).getName());
                    intent1.putExtra("className", learnerList.get(randomValue).getClassname());
                    intent1.putExtra("languageInstruction", learnerList.get(randomValue).getLanguage());
                    intent1.putExtra("birthdate", learnerList.get(randomValue).getBirthdate());
                    intent1.putExtra("alergiesOfLearner", learnerList.get(randomValue).getLearner_allergy());
                    intent1.putExtra("dateEnrolled", learnerList.get(randomValue).getEnrolment_date());
                    intent1.putExtra("doctorContactNumber", learnerList.get(randomValue).getDoctor_cell());
                    intent1.putExtra("medicalAidName", learnerList.get(randomValue).getMedical_name());
                    intent1.putExtra("medicalAidNumber", learnerList.get(randomValue).getMedical_number());
                    intent1.putExtra("medicalAidPlan", learnerList.get(randomValue).getMedical_plan());
                    intent1.putExtra("doctorName", learnerList.get(randomValue).getDoctor_name());
                    intent1.putExtra("fatherName", learnerList.get(randomValue).getFather_name());
                    intent1.putExtra("motherName", learnerList.get(randomValue).getMother_name());
                    intent1.putExtra("idNumber", learnerList.get(randomValue).getId_number());
                    intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    PendingIntent pendingIntent1 = PendingIntent.getActivity(context, 2, intent1, 0);
                    views.setOnClickPendingIntent(R.id.wig, pendingIntent1);

                    appWidgetManager.updateAppWidget(appWidgetId, views);
                }

                @Override
                public void handleFault(BackendlessFault backendlessFault) {

                }
            });
        }
    }
}
