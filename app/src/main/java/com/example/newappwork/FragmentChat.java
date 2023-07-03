package com.example.newappwork;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public class FragmentChat extends Fragment {



    public FragmentChat() {
        // Required empty public constructor
    }

    RecyclerView recview;
    MessageAdapter messageAdapter;
    EditText editText;
    Button button;
    private ImageView iv_mic;
    private static final int REQUEST_CODE_SPEECH_INPUT = 1;

    private String apiurl = "https://api.openai.com/v1/completions";
    String accessToken="";
    List<Message> messageList;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chat, container, false);

        recview=view.findViewById(R.id.recycler_view);
        recview.setLayoutManager(new LinearLayoutManager(getContext()));
        editText=view.findViewById(R.id.edit_text);
        button=view.findViewById(R.id.button);
        iv_mic = view.findViewById(R.id.iv_mic);

        messageList=new ArrayList<>();
        messageAdapter=new MessageAdapter(messageList);
        recview.setAdapter(messageAdapter);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProcessAI();
            }
        });

        iv_mic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent
                        = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                        RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,
                        Locale.getDefault());
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak to text");

                try {
                    startActivityForResult(intent, REQUEST_CODE_SPEECH_INPUT);
                }
                catch (Exception e) {
                    Toast.makeText(getContext(), " " + e.getMessage(),
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    public void onActivityResult(int requestCode, int resultCode,
                                    @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_SPEECH_INPUT) {
            if (resultCode == RESULT_OK && data != null) {
                ArrayList<String> result = data.getStringArrayListExtra(
                        RecognizerIntent.EXTRA_RESULTS);
                editText.setText(
                        Objects.requireNonNull(result).get(0));
            }
        }
    }


    public void ProcessAI()
    {
        String text=editText.getText().toString();
        messageList.add(new Message(text,true));
        messageAdapter.notifyItemInserted(messageList.size()-1);
        recview.scrollToPosition(messageList.size()-1);
        editText.getText().clear();

        JSONObject requestBody=new JSONObject();
        try {
            requestBody.put("model", "text-davinci-003");
            requestBody.put("prompt", text);
            requestBody.put("max_tokens", 1000);
            requestBody.put("temperature", 1);
          //  requestBody.put("top_p", 1);
         //   requestBody.put("frequency_penalty", 0.0);
          // requestBody.put("presence_penalty", 0.0);

        }catch (Exception ex)
        {
            ex.printStackTrace();
        }

        JsonObjectRequest request=new JsonObjectRequest(Request.Method.POST, apiurl, requestBody, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray js = response.getJSONArray("choices");
                    JSONObject jsonObject=js.getJSONObject(0);
                    String text=jsonObject.getString("text");
                    messageList.add(new Message(text.replaceFirst("\n", "").replaceFirst("\n", ""), false));
                    messageAdapter.notifyItemInserted(messageList.size() - 1);
                    recview.scrollToPosition(messageList.size()-1);
                }catch (Exception ex)
                {
                    ex.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("API Error",error.toString());
                messageList.add(new Message(error.toString().replaceFirst("\n", "").replaceFirst("\n", ""), false));
                messageAdapter.notifyItemInserted(messageList.size() - 1);
                recview.scrollToPosition(messageList.size()-1);
            }
        })
        {
            public Map<String,String> getHeaders() throws AuthFailureError
            {
                Map<String,String> headers= new HashMap<>();
                headers.put("Authorization","Bearer "+accessToken);
                headers.put("Content-Type","application/json");
                return  headers;
            }
            @Override
            protected Response < JSONObject > parseNetworkResponse(NetworkResponse response) {
                return super.parseNetworkResponse(response);
            }
        };

        int timeoutMs = 25000; // 25 seconds timeout
        RetryPolicy policy = new DefaultRetryPolicy(timeoutMs, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        request.setRetryPolicy(policy);
        // Add the request to the RequestQueue
        MySingleton.getInstance(getActivity()).addToRequestQueue(request);
    }




}
