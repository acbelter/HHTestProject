/*
 * Copyright (c) 2013, acbelter
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
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
                Linkify.addLinks(phoneText, Pattern.compile("\\+\\d*"), "tel:");
            }
            if (extras.containsKey(CvActivity.EMAIL)) {
                emailText.setAutoLinkMask(Linkify.EMAIL_ADDRESSES);
                emailText.setText(extras.getString(CvActivity.EMAIL));
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
