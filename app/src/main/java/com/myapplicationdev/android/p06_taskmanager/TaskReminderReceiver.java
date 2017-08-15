package com.myapplicationdev.android.p06_taskmanager;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.app.RemoteInput;

public class TaskReminderReceiver extends BroadcastReceiver {

    int notifReqCode = 123;
	int notificationId = 001; // A unique ID for our notification
	@Override
	public void onReceive(Context context, Intent i) {

		int id = i.getIntExtra("id", -1);
		String name = i.getStringExtra("name");
		String desc = i.getStringExtra("desc");

		Intent intent = new Intent(context, MainActivity.class);
		PendingIntent pIntent = PendingIntent.getActivity(context, 0, intent, 0);

		// build notification
		NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
		builder.setContentTitle("Task Manager Reminder");
		builder.setContentText(name);
		builder.setSmallIcon(android.R.drawable.ic_dialog_info);
		builder.setContentIntent(pIntent);
		builder.setAutoCancel(true);

		Notification n = builder.build();

		NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		// You may put an ID for the first parameter if you wish
		// to locate this notification to cancel
		notificationManager.notify(notifReqCode, n);


		// Starts HERE ----------------------------------------------------------------------------------------------------------

		//This is used to link, so when clicked it would open the app on the phone
		Intent intent1 = new Intent(context, MainActivity.class);
		PendingIntent pendingIntent =
				PendingIntent.getActivity(context, 0,
						intent1, PendingIntent.FLAG_UPDATE_CURRENT);

		NotificationCompat.Action action = new
				NotificationCompat.Action.Builder(
				R.mipmap.ic_launcher,
				"P06 Task Manager",
				pendingIntent).build();

		//Reply Function
		Intent intentreply = new Intent(context,
				ReplyActivity.class);

		intentreply.putExtra("id", id);

		PendingIntent pendingIntentReply = PendingIntent.getActivity
				(context, notifReqCode, intentreply,
						PendingIntent.FLAG_UPDATE_CURRENT);

		RemoteInput ri = new RemoteInput.Builder("status")
				.setLabel("Status report")
				.setChoices(new String [] {"Completed", "Not yet"})
				.build();

		NotificationCompat.Action action2 = new
				NotificationCompat.Action.Builder(
				R.mipmap.ic_launcher,
				"Reply",
				pendingIntentReply)
				.addRemoteInput(ri)
				.build();


// To display both functions on the watch
		NotificationCompat.WearableExtender extender = new
				NotificationCompat.WearableExtender();
		extender.addAction(action);
		extender.addAction(action2);


// What happens when the Reply functions is clicked, it would open up the other options inside it
		Notification notification = new
				NotificationCompat.Builder(context)
				.setContentText(desc)
				.setContentTitle(name)
				.setSmallIcon(R.mipmap.ic_launcher)
				// When Wear notification is clicked, it performs
				// the action we defined in line below
				.extend(extender)
				.build();

		// TO create the Notification Object
		NotificationManagerCompat notificationManagerCompat =
				NotificationManagerCompat.from(context);
		notificationManagerCompat.notify(notificationId, notification);

	}

}
