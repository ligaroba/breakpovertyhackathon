package root.com.jiranimmicrocredit;
import android.support.v4.app.Fragment;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class EmailFragment extends Fragment{
    Button buttonSend;
    EditText textTo;
    EditText textSubject;
    EditText textMessage;

    public EmailFragment() {
        super();
    }

    int color;

    @SuppressLint("ValidFragment")
    public EmailFragment(int color) {
        this.color = color;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View RootView=inflater.inflate(R.layout.fragment_email,container,false);

        buttonSend=(Button)RootView.findViewById(R.id.buttonSend);
        textTo = (EditText) RootView.findViewById(R.id.editTextTo);
        textSubject = (EditText) RootView.findViewById(R.id.editTextSubject);
        textMessage=(EditText)RootView.findViewById(R.id.editTextMessage);

        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String to=textTo.getText().toString();
                String subject=textSubject.getText().toString();
                String message=textMessage.getText().toString();

                Intent email=new Intent(Intent.ACTION_SEND);

                email.putExtra(Intent.EXTRA_EMAIL,new String[]{to});

                email.putExtra(Intent.EXTRA_SUBJECT,subject);
                email.putExtra(Intent.EXTRA_TEXT, message);
                email.setType("message/rfc822");

                startActivity(Intent.createChooser(email, "Choose an Email client :"));

            }
        });
        // final FrameLayout frameLayout = (FrameLayout) RootView.findViewById(R.id.dummyfrag_bg);
        //frameLayout.setBackgroundColor(color);
        return RootView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
