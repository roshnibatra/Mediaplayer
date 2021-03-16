package mediaSession
//
//import android.content.Context
//import android.support.v4.media.session.MediaSessionCompat
//import android.support.v4.media.session.PlaybackStateCompat
//import androidx.core.app.NotificationCompat
//import androidx.media.session.MediaButtonReceiver
//
//class MediaStyleHelper(musicService: MusicService, mediaSessionCompat: MediaSessionCompat) {
//
//    companion object fun mediaStyle(
//        context: Context, mediaSession: MediaSessionCompat
//    ):NotificationCompat.Builder {
//        val controller = mediaSession.getController()
//        val mediaMetadata = controller.getMetadata()
//        val description = mediaMetadata.getDescription()
//        val builder = NotificationCompat.Builder(context)
//        builder
//            .setContentTitle(description.getTitle())
//            .setContentText(description.getSubtitle())
//            .setSubText(description.getDescription())
//            .setLargeIcon(description.getIconBitmap())
//            .setContentIntent(controller.getSessionActivity())
//            .setDeleteIntent(
//                MediaButtonReceiver.buildMediaButtonPendingIntent(context, PlaybackStateCompat.ACTION_STOP))
//            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
//        return builder
//    }
//}