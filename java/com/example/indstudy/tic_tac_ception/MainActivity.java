package com.example.indstudy.tic_tac_ception;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Space;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.view.View.OnClickListener;


public class MainActivity extends Activity implements View.OnClickListener{
    int width, height, index, position = -1;
    Button buttons[] = new Button[81];
    RelativeLayout grids[] = new RelativeLayout[9];
    //GridLayout imageGrids[] = new GridLayout[9];
    Board boards[] = new Board[9];
    Board outerBoard = new Board();
    ImageView oh[] = new ImageView[9];
    ImageView ex[] = new ImageView[9];
    GradientDrawable shape, shape2, draw;
    boolean isX = true, won = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        width = size.x;
        height = size.y;
        shape = (GradientDrawable)getResources().getDrawable(R.drawable.line);
        shape2 = (GradientDrawable)getResources().getDrawable(R.drawable.rect);

        index = 0;
        for(int layout = 0; layout < 9; layout++)
        {
            grids[layout] = new RelativeLayout(this);
            GridLayout gl = new GridLayout(this);
            //imageGrids[layout] = gl;
            gl.setId(layout + 81);
            gl.setRowCount(3);
            gl.setColumnCount(3);
            grids[layout].addView(gl);


            boards[layout] = new Board();

            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(width/3, height/3);

            params.addRule(RelativeLayout.ALIGN_TOP, layout + 81);
            params.addRule(RelativeLayout.ALIGN_BOTTOM, layout + 81);
            params.addRule(RelativeLayout.ALIGN_LEFT, layout + 81);
            params.addRule(RelativeLayout.ALIGN_RIGHT, layout + 81);

            oh[layout] = new ImageView(this);
            oh[layout].setLayoutParams(params);
            oh[layout].setImageDrawable(getResources().getDrawable(R.drawable.oo));
            oh[layout].setVisibility(View.GONE);

            ex[layout] = new ImageView(this);
            ex[layout].setImageDrawable(getResources().getDrawable(R.drawable.xx));
            ex[layout].setLayoutParams(params);
            ex[layout].setVisibility(View.GONE);

            grids[layout].addView(oh[layout]);
            grids[layout].addView(ex[layout]);

            if(layout%2 == 0)
            {
                draw = shape2;
            }
            else
                draw = shape;

            for (int i = 0; i < 9; i++) {
                buttons[index] = new Button(this);
                buttons[index].setBackground(draw);
                buttons[index].setHeight(height / 9);
                buttons[index].setWidth(width / 9);
               // buttons[index].setText("" + index);
                buttons[index].setId(index);
                buttons[index].setOnClickListener((android.view.View.OnClickListener) this);
                gl.addView(buttons[index], i);
                index++;
            }
            ((GridLayout)findViewById(R.id.layout)).addView(grids[layout], layout);
        }
    }
//Divide by 9 to get grid and mod by 9 to get button position

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if(id == R.id.reset)
        {
            for (int i = 0; i < buttons.length; i++) {
                buttons[i].setText("");

            }

            for (int i = 0; i < boards.length; i++) {
                boards[i].clear();
                grids[i].setBackgroundColor(Color.BLACK);
            }
            position = -1;
            outerBoard.clear();
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    public synchronized void onClick(View v) {
        if(won)
            return;

        //Variables
        int currentGrid, oldPosition;
        currentGrid = v.getId()/9;
        //In case they try and cheat
        oldPosition = position;

        //Stop from playing on won/finished board
        if(outerBoard.getButtonValue(currentGrid) != 0 && (position != -1 && outerBoard.getButtonValue(position) != 3))
            return;

        if(position != -1)
        {
            if (oldPosition != currentGrid)
                return;
        }

        position = v.getId()%9;

        if(boards[currentGrid].getButtonValue(position) != 0)
        {
            position = oldPosition;
            return;
        }

        if (isX)
        {
            ((Button) v).setText("X");
        } else
            ((Button) v).setText("O");

        grids[currentGrid].setBackgroundColor(Color.BLACK);
        if(boards[currentGrid].play(position, isX? 1:2))
        {
            if(outerBoard.play(currentGrid, isX? 1:2))
            {
                ((Button) v).setText("Win");
                won = true;
            }
            else
            {
                if(outerBoard.checkFinished())
                {
                    ((Button) v).setText("Cat");
                }
                else
                {
                    grids[position].setBackgroundColor(Color.WHITE);
                }
            }
            grids[currentGrid].findViewById(81+currentGrid).setVisibility(View.GONE);
            if(isX)
            {
                ex[currentGrid].setVisibility(View.VISIBLE);
            }
            else
            {
                oh[currentGrid].setVisibility(View.VISIBLE);
            }
        }
        else {
            if(boards[currentGrid].checkFinished())
            {
                outerBoard.play(currentGrid, 3);
                grids[currentGrid].setBackgroundColor(Color.RED);
                grids[position].setBackgroundColor(Color.WHITE);
            }
            else
            {
                grids[position].setBackgroundColor(Color.WHITE);
            }
        }

        isX = !isX;



            if (outerBoard.getButtonValue(position) != 0) {
                position = -1;
            }

    }
}
