package com.swj.tp07matchpicrefectoring;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.swj.tp07matchpicrefectoring.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    ImageView[] ivAnimalArr;
    SoundPool sp;
    int sdStart, sdSelect, sdAgain, sdGoodJob;
    ArrayList<Integer> animalArr = new ArrayList<>();
    ArrayList<Integer> animalBoardArr = new ArrayList<>();
    int animalCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        ivAnimalArr = new ImageView[5];
        ivAnimalArr[0] = binding.ivAnimal01;
        ivAnimalArr[1] = binding.ivAnimal02;
        ivAnimalArr[2] = binding.ivAnimal03;
        ivAnimalArr[3] = binding.ivAnimal04;
        ivAnimalArr[4] = binding.ivAnimal05;

        for (ImageView imageView : ivAnimalArr) imageView.setOnClickListener(listener);

        SoundPool.Builder builder = new SoundPool.Builder();
        builder.setMaxStreams(10);
        sp = builder.build();
        sdStart = sp.load(this, R.raw.s_sijak, 0);
        sdSelect = sp.load(this, R.raw.s_select, 0);
        sdAgain = sp.load(this, R.raw.s_again, 0);
        sdGoodJob = sp.load(this, R.raw.s_goodjob, 0);

        for(int i=0; i<ivAnimalArr.length; i++)
            animalArr.add(R.drawable.a_ele + i);

        for(int i=0; i<ivAnimalArr.length; i++)
            animalBoardArr.add(R.drawable.b_ele + i);

        binding.ivStart.setOnClickListener(view -> {
            binding.ivHowto.setVisibility(View.VISIBLE);
            animalShuffle();
            sp.play(sdStart, 0.9f, 0.9f, 3, 0, 1.0f );
        });

        binding.ivHowto.setOnClickListener(view ->
            new AlertDialog.Builder(MainActivity.this)
                    .setView(R.layout.dialog)
                    .setPositiveButton("확인",(dialogInterface, i) -> {})
                    .create()
                    .show()
        );
    }

    private void animalShuffle() {
        Collections.shuffle(animalArr);

        for(int i=0; i<ivAnimalArr.length; i++) {
            ivAnimalArr[i].setImageResource(animalArr.get(i));
            ivAnimalArr[i].setTag(animalArr.get(i));
        }

        binding.ivBoard.setImageResource(animalBoardArr.get(animalCount));

        animalArr.clear();
        for(int i=0; i<ivAnimalArr.length; i++)
            animalArr.add(R.drawable.a_ele + i);
        binding.ivBoard.setTag(animalArr.get(animalCount));

        animalCount++;
        if(animalCount > 4) animalCount = 0;
    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int myChoiceAnimalTag = (Integer) view.getTag();
            int animalBoardTag = (Integer) binding.ivBoard.getTag();

            if(myChoiceAnimalTag == animalBoardTag) {
                sp.play(sdGoodJob, 0.8f, 0.8f, 2, 0, 1.0f);
                animalShuffle();
            } else sp.play(sdAgain, 0.8f, 0.8f, 2, 0, 1.0f);

        }
    };
}