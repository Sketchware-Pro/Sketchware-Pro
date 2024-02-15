package mod.codeware;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.ai.client.generativeai.GenerativeModel;
import com.google.ai.client.generativeai.java.GenerativeModelFutures;
import com.google.ai.client.generativeai.type.Content;
import com.google.ai.client.generativeai.type.GenerateContentResponse;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class HiveAI {
    private final String apiKey = "AIzaSyB7PhMG67AJQi_EMC1WP5AJNCVsT4RqBqs";
    private static final String TAG = "GenerativeAI";

    public void generate(String question, CodeHiveListener codeHiveListener) {

        String prompt = "You are an experienced Android Java code generator, capable of providing professional and error-free code snippets. When asked for code, your responses should exclude any documentation or explanations, and instead focus on providing the requested functions or statements. Only give function if i asked, else give as statement. Code must not contains kotlin. Here's the question :" + question.trim();

        // For text-only input, use the gemini-pro model
        GenerativeModel gm = new GenerativeModel("gemini-pro",
                // Paste your API key here
                apiKey);
        GenerativeModelFutures model = GenerativeModelFutures.from(gm);


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
                String code = result.getText();
                assert code != null;
                if (getFirstWord(code).contains("```")) {
                    code = code.replace(getFirstWord(code), "");
                }
                codeHiveListener.onSuccess(code.replace("```", "").trim());
            }

            @Override
            public void onFailure(@NonNull Throwable t) {
                t.printStackTrace();
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

    public String getFirstWord(String text) {
        return text.split("\n")[0];
    }

    public interface CodeHiveListener {
        void onSuccess(String resultText);
    }
}
