package chat.namespace;

import org.jivesoftware.smack.Connection;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.filter.MessageTypeFilter;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ChatActivity extends Activity {
	EditText etext;
	TextView tv;
	Connection connection;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		etext = (EditText) findViewById(R.id.editText1);
		tv = (TextView) findViewById(R.id.textView1);
		Button btn = (Button) findViewById(R.id.button1);
		try {
			 ConnectionConfiguration config = new ConnectionConfiguration(
					 "ppl.eln.uniroma2.it", 5222);
					 config.setSecurityMode(ConnectionConfiguration.SecurityMode.disabled);
					 connection = new XMPPConnection(config);
					 connection.connect();
					 connection.login("rossi", "rossi");
					 connection.addPacketListener(new PacketListener() {
			
				public void processPacket(Packet pkt) {
					Message msg = (Message) pkt;
					String from = msg.getFrom();
					String body = msg.getBody();
					tv.append(from + " : " + body + "\n");
				}
			}, new MessageTypeFilter(Message.Type.normal));
		} 
		catch (XMPPException e) {
			e.printStackTrace();
		}
		btn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				tv.append("ME: " + etext.getText().toString() + "\n");
				try	{
					Message msg = new Message();
					msg.setTo("loreti@ppl.eln.uniroma2.it");
					msg.setBody(etext.getText().toString());
					connection.sendPacket(msg);
				}
				catch (Exception e)	{
					e.printStackTrace();
					Toast.makeText(getApplicationContext(), "IMPOSSIBILE STABILIRE UNA CONNESSIONE", Toast.LENGTH_LONG).show();
				}

			}
		});
	}
}