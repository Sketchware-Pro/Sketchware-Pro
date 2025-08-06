package dev.aldi.sayuti.editor.view.palette;

import android.content.Context;
import android.view.ViewGroup;

import com.besome.sketch.beans.ViewBean;
import com.besome.sketch.editor.view.palette.IconBase;

import mod.agus.jcoderz.beans.ViewBeans;
import pro.sketchware.R;

public class IconYoutubePlayer extends IconBase {

    public IconYoutubePlayer(Context context) {
        super(context);
        setWidgetImage(R.drawable.ic_mtrl_youtube);
        setWidgetName("YoutubePlayer");
    }

    @Override
    public ViewBean getBean() {
        ViewBean viewBean = new ViewBean();
        viewBean.type = ViewBeans.VIEW_TYPE_WIDGET_YOUTUBEPLAYERVIEW;
        viewBean.layout.width = ViewGroup.LayoutParams.MATCH_PARENT;
        viewBean.layout.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        viewBean.text.text = getName();
        viewBean.convert = "com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView";
        viewBean.inject = "app:autoPlay=\"false\"\napp:useWebUi=\"false\"\napp:showYouTubeButton=\"true\"\napp:showFullScreenButton=\"true\"\napp:showVideoCurrentTime=\"true\"\napp:showVideoDuration=\"true\"\napp:showSeekBar=\"true\"\napp:handleNetworkEvents=\"false\"\napp:enableAutomaticInitialization=\"true\"";
        return viewBean;
    }
}
