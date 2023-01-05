package com.rvgroup.stickynotes;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.rvgroup.stickynotes.activities.DataInsertActivity;
import com.rvgroup.stickynotes.activities.Note;
import com.rvgroup.stickynotes.adapters.RvAdapter;
import com.rvgroup.stickynotes.databinding.ActivityMainBinding;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    private NoteViewModel noteViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();
        noteViewModel = new ViewModelProvider(this, (ViewModelProvider.Factory) ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication()))
                .get(NoteViewModel.class);

        binding.floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, DataInsertActivity.class);
                intent.putExtra("type","addMode");
                startActivityForResult(intent,1);

            }
        });

        binding.mainrecycler.setLayoutManager(new LinearLayoutManager(this));
        binding.mainrecycler.setHasFixedSize(true);
        RvAdapter adapter = new RvAdapter();
        binding.mainrecycler.setAdapter(adapter);

        //datafetch
        noteViewModel.getAllNotelist().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                adapter.submitList(notes);
            }
        });

        //
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                if (direction==ItemTouchHelper.RIGHT){
                    noteViewModel.delete(adapter.getNote(viewHolder.getAdapterPosition()));
                    Toast.makeText(MainActivity.this,"Note Deleted",Toast.LENGTH_SHORT).show();

                }
                else{
                    Intent intent = new Intent(MainActivity.this,DataInsertActivity.class);
                    intent.putExtra("type","update");
                    intent.putExtra("title",adapter.getNote(viewHolder.getAdapterPosition()).getTitle());
                    intent.putExtra("body",adapter.getNote(viewHolder.getAdapterPosition()).getBody());
                    intent.putExtra("id",adapter.getNote(viewHolder.getAdapterPosition()).getId());
                    startActivityForResult(intent, 2);
//                    noteViewModel.delete(adapter.getNote(viewHolder.getAdapterPosition()));
//                Toast.makeText(MainActivity.this,"Note Deleted",Toast.LENGTH_SHORT).show();
                }
            }
        }).attachToRecyclerView(binding.mainrecycler);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==1){
            String title = data.getStringExtra("title");
            String body = data.getStringExtra("body");
            Note note = new Note(title,body);
            noteViewModel.insert(note);
            Toast.makeText(this, "Note Added", Toast.LENGTH_SHORT).show();
        }
        else if (requestCode==2){
            String title = data.getStringExtra("title");
            String body = data.getStringExtra("body");
            Note note = new Note(title,body);
            note.setId(data.getIntExtra("id",0));
            noteViewModel.update(note);
            Toast.makeText(this, "Note updated", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        finishAffinity();
    }
}