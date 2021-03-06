package com.example.new_application;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentMain#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentMain extends Fragment {

    ArrayList<Converstion> user_arrayList;
    ArrayList<ChatList> user_stringList;
    RecyclerView recyclerView ;
    FirebaseUser user;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentMain() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentMain.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentMain newInstance(String param1, String param2) {
        FragmentMain fragment = new FragmentMain();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view =inflater.inflate(R.layout.fragment_main, container, false);
        recyclerView=view.findViewById(R.id.recycler_user_chats);
        user_arrayList=new ArrayList<>();
        user_stringList=new ArrayList<>();

        user= FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("chatlist")
                .child(user.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                user_stringList.clear();
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    ChatList chatList = dataSnapshot.getValue(ChatList.class);
                    if (chatList.getId()!=null)
                     Log.d("ket", chatList.getId() );
                    Toast.makeText(getContext(), "id:   "+chatList.getId(), Toast.LENGTH_SHORT).show();
                   user_stringList.add(chatList);

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });


        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        show_user();
    }

    public  void show_user(){

        DatabaseReference reference=FirebaseDatabase.getInstance().getReference("Con");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                user_arrayList.clear();
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    Converstion converstion=dataSnapshot.getValue(Converstion.class);
                    for (ChatList chatList : user_stringList){
                        if (chatList.getId().equals(converstion.getId())){
                             user_arrayList.add(converstion);

                        }
                    }
                }
                AdapterRecuclerUser adpter =new AdapterRecuclerUser(getContext(),user_arrayList, false);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                adpter.notifyDataSetChanged( );
                recyclerView.setAdapter(adpter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}