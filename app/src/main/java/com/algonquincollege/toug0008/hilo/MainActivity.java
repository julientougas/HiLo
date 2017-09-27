/**
 *  HiLo Game for android class
 *  @author Julien Tougas (toug0008@algonquinlive.com)
 */
package com.algonquincollege.toug0008.hilo;

import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.util.Random;

public class MainActivity extends Activity {

    private static final String ABOUT_DIALOG_TAG = "About Dialog";

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_about) {
            DialogFragment newFragment = new AboutDialogFragment();
            newFragment.show(getFragmentManager(), ABOUT_DIALOG_TAG);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    Random rand = new Random();

    int theNumber = rand.nextInt(1000) + 1;


    private EditText userGuessEditText;
    public static int minNum = 1;
    public static int maxNum = 1000;
    public static int counter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userGuessEditText = (EditText) findViewById(R.id.userGuess);

        final Button submitGuess = (Button) findViewById(R.id.submitGuess);
        submitGuess.setText("Submit Guess");

        submitGuess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //check if clicking reset
                if (submitGuess.getText().toString() != "Submit Guess"){
                    submitGuess.setText("Submit Guess");
                    theNumber = rand.nextInt(1000) + 1;
                    return;
                }
                counter += 1;
                Log.i("itag", "Counter: " + counter);
                Log.i("itag", "Magic Number: " + theNumber);
                //check how many guesses
                if (counter < 10) {

                    String userGuessString = userGuessEditText.getText().toString();

                    //validation
                    if (userGuessString.isEmpty()) {
                        Log.i("itag", "guess is empty");
                        userGuessEditText.setError("Please Enter a Number");
                        userGuessEditText.requestFocus();
                        return;
                    }

                    int userGuessInt;

                    try {
                        userGuessInt = Integer.parseInt(userGuessString);
                    } catch (NumberFormatException e) {
                        userGuessEditText.setError("Please Enter a Number");
                        userGuessEditText.requestFocus();
                        return;
                    }

                    if ((userGuessInt < 1) || (userGuessInt > 1000)){
                        userGuessEditText.setError("Please Enter a Number between 1 and 1000");
                        userGuessEditText.requestFocus();
                        return;
                    }
                    //end validation

                    //handle guess
                    if (userGuessInt > theNumber) {
                        maxNum = theNumber;
                        Toast.makeText(getApplicationContext(),
                                "Too high!",
                                Toast.LENGTH_LONG).show();
                    } else if (userGuessInt < theNumber) {
                        minNum = theNumber;
                        Toast.makeText(getApplicationContext(),
                                "Too low!",
                                Toast.LENGTH_LONG).show();
                    } else if ((userGuessInt == theNumber) && counter <= 5) {
                        Toast.makeText(getApplicationContext(),
                                "Superior Win!",
                                Toast.LENGTH_LONG).show();
                        resetGame();
                    } else if ((userGuessInt == theNumber) && counter > 5){
                        Toast.makeText(getApplicationContext(),
                                "Excellent Win!",
                                Toast.LENGTH_LONG).show();
                        resetGame();
                    }
                    //end handle guess
                } else {
                    resetGame();
                    Toast.makeText(getApplicationContext(),
                            "Fail! Please Reset",
                            Toast.LENGTH_LONG).show();
                }

                userGuessEditText.setText( "" );
            }

            private void resetGame(){
                submitGuess.setText("Please Reset");
                counter = 0;
            }

        });
        submitGuess.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Toast.makeText(getApplicationContext(),
                        "The Number: " + theNumber,
                        Toast.LENGTH_LONG).show();
                return true;
            }
        });

    } //end onCreate
}
