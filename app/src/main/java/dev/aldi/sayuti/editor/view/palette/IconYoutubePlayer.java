package dev.aldi.sayuti.editor.view.palette;

import android.content.Context;

import com.besome.sketch.beans.LayoutBean;
import com.besome.sketch.beans.ViewBean;
import com.besome.sketch.editor.view.palette.IconBase;

public class IconYoutubePlayer extends IconBase {
    public IconYoutubePlayer(Context context) {
        super(context);
    }

    public void a(Context context) {
        IconYoutubePlayer.super.a(context);
        setWidgetImage(2131166356);
        setWidgetName("YoutubePlayer");
    }

    public ViewBean getBean() {
        ViewBean viewBean = new ViewBean();
        viewBean.type = 45;
        LayoutBean layoutBean = viewBean.layout;
        layoutBean.width = -1;
        layoutBean.height = -2;
        viewBean.text.text = getName();
        viewBean.convert = "com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView";
        viewBean.inject = "app:autoPlay=\"false\"\napp:useWebUi=\"false\"\napp:showYouTubeButton=\"true\"\napp:showFullScreenButton=\"true\"\napp:showVideoCurrentTime=\"true\"\napp:showVideoDuration=\"true\"\napp:showSeekBar=\"true\"\napp:handleNetworkEvents=\"false\"\napp:enableAutomaticInitialization=\"true\"";
        return viewBean;
    }
}
