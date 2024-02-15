package mod.codeware;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.ai.client.generativeai.GenerativeModel;
import com.google.ai.client.generativeai.java.GenerativeModelFutures;
import com.google.ai.client.generativeai.type.Content;
import com.google.ai.client.generativeai.type.GenerateContentResponse;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.sketchware.remod.R;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class GenerativeAI {
    private final String apiKey = "AIzaSyB7PhMG67AJQi_EMC1WP5AJNCVsT4RqBqs";
    private static final String TAG = "GenerativeAI";

    public void generate(Context context, String question) {

        String prompt = "I will give you questions for java code for android. you give me the code without explanation or documentation. give code as simple statements unless i ask for function\n Question: " + question;

        // For text-only input, use the gemini-pro model
        GenerativeModel gm = new GenerativeModel("gemini-pro",
                // Paste your API key here
                apiKey);
        GenerativeModelFutures model = GenerativeModelFutures.from(gm);

        Bitmap image = BitmapFactory.decodeResource(context.getResources(), R.drawable.abc_96_color);

        Content content = new Content.Builder()
                .addText(prompt)
                .build();


        /*Publisher<GenerateContentResponse> streamingResponse =
                model.generateContentStream(content);

        final String[] fullResponse = {""};

        Log.d(TAG, "generate: " + question);

        streamingResponse.subscribe(new Subscriber<>() {
            @Override
            public void onSubscribe(Subscription s) {
                s.request(1);
            }

            @Override
            public void onNext(GenerateContentResponse generateContentResponse) {
                String chunk = generateContentResponse.getText();
                fullResponse[0] += chunk;

                Log.d(TAG, "onNext: " + chunk);
            }

            @Override
            public void onComplete() {
                System.out.println(fullResponse[0]);
                Log.d(TAG, "onComplete: " + fullResponse[0]);
            }

            @Override
            public void onError(Throwable t) {
                Log.d(TAG, "onError: " + t.getMessage());
            }

        });
*/

        Executor executor = Executors.newSingleThreadExecutor();
        ListenableFuture<GenerateContentResponse> response = model.generateContent(content);
        Futures.addCallback(response, new FutureCallback<>() {
            @Override
            public void onSuccess(GenerateContentResponse result) {
                String resultText = result.getText();
                Log.d(TAG, "onSuccess: " + resultText);
            }

            @Override
            public void onFailure(@NonNull Throwable t) {
                t.printStackTrace();
                Log.e(TAG, "onFailure: ", t);
            }
        }, executor);

/*
        Publisher<GenerateContentResponse> publisher = model.generateContentStream(content);

        publisher.subscribe(new Subscriber<>() {
            @Override
            public void onSubscribe(org.reactivestreams.Subscription s) {
                s.request(1);
            }

            @Override
            public void onNext(GenerateContentResponse generateContentResponse) {
                String resultText = generateContentResponse.getText();
                Log.d(TAG, "c: " + resultText);
            }

            @Override
            public void onError(Throwable t) {
                t.printStackTrace();
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete: ");
            }
        });*/
    }
}
