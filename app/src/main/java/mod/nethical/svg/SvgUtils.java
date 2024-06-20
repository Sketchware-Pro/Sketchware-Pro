package mod.nethical.svg;

import android.content.Context;
import android.widget.ImageView;
import coil.ComponentRegistry;
import coil.decode.SvgDecoder;
import java.io.File;
import coil.ImageLoader;
import coil.request.ImageRequest;

public class SvgUtils {
    
    private Context context;
    private ImageLoader imageLoader;
    
    public SvgUtils(Context context){
        this.context = context;
        initImageLoader();
        
    }
    public void initImageLoader(){
        imageLoader = new ImageLoader.Builder(context)
                .components(new ComponentRegistry.Builder()
                    .add(new SvgDecoder.Factory())
                    .build())
                .build();
    }
    
    
    public void loadImage(ImageView imageView, String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            ImageRequest request = new ImageRequest.Builder(context)
                .data(file)
                .target(imageView)
                .build();
    
            imageLoader.enqueue(request);
        } 
    }
    
}
