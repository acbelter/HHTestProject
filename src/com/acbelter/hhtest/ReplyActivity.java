/*
 * Copyright 2013 acbelter
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.acbelter.hhtest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.text.util.Linkify;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.acbelter.hhtest.R.id;

import java.util.Calendar;
import java.util.regex.Pattern;

public class ReplyActivity extends Activity {
    public static final String REPLY = "com.acbelter.hhtest.REPLY";
    private EditText mReplyText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reply);

        TextView nameText = (TextView) findViewById(id.name_text);
        TextView birthDateText = (TextView) findViewById(id.birth_date_text);
        TextView sexText = (TextView) findViewById(id.sex_text);
        TextView officeText = (TextView) findViewById(id.office_text);
        TextView salaryText = (TextView) findViewById(id.salary_text);
        TextView phoneText = (TextView) findViewById(id.phone_text);
        TextView emailText = (TextView) findViewById(id.email_text);
        mReplyText = (EditText) findViewById(id.reply);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            nameText.setText(extras.getString(CvActivity.NAME));

            int year = extras.getInt(CvActivity.BD_YEAR, -1);
            int month = extras.getInt(CvActivity.BD_MONTH, -1);
            int day = extras.getInt(CvActivity.BD_DAY, -1);

            // If the date of birth was received.
            if (year > 0 && month > 0 && day > 0) {
                Calendar c = Calendar.getInstance();
                c.set(Calendar.YEAR, year);
                c.set(Calendar.MONTH, month);
                c.set(Calendar.DAY_OF_MONTH, day);

                CharSequence formatDate = DateFormat.format("dd.MM.yyyy", c);
                birthDateText.setText(formatDate);
            }

            if (extras.containsKey(CvActivity.SEX)) {
                sexText.setText(extras.getString(CvActivity.SEX));
            }
            if (extras.containsKey(CvActivity.OFFICE)) {
                officeText.setText(extras.getString(CvActivity.OFFICE));
            }
            if (extras.containsKey(CvActivity.SALARY)) {
                salaryText.setText(extras.getString(CvActivity.SALARY));
            }
            if (extras.containsKey(CvActivity.PHONE)) {
                phoneText.setText(extras.getString(CvActivity.PHONE));
                // phoneText has already correct phone number.
                // Note: see CvActivity.checkInputData()
                Linkify.addLinks(phoneText, Pattern.compile("^.+$"), "tel:");
            }
            if (extras.containsKey(CvActivity.EMAIL)) {
                emailText.setText(extras.getString(CvActivity.EMAIL));
                // emailText has already correct e-mail address.
                // Note: see CvActivity.checkInputData()
                Linkify.addLinks(emailText, Pattern.compile("^.+$"), "mailto:");
            }
        }
    }

    /**
     * Called when user press the button for sending the employer's reply.
     * @param view Clicked button view.
     */
    public void sendReply(View view) {
        Intent replyIntent = new Intent();
        replyIntent.putExtra(REPLY, mReplyText.getText().toString());
        setResult(RESULT_OK, replyIntent);
        finish();
    }
}
