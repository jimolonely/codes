package com.jimo;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.observers.DisposableMaybeObserver;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 * @author jimo
 * @date 19-8-19 下午5:40
 */
public class TestRxTest {

    @Test
    public void testSingle() throws InterruptedException {

        // observable
        Single<String> single = Single.just("hello jimo");

        // observer
        DisposableSingleObserver<String> observer = single
                .delay(2, TimeUnit.SECONDS, Schedulers.io())
                .subscribeWith(new DisposableSingleObserver<String>() {
                    @Override
                    public void onSuccess(String s) {
                        System.out.println(s);
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        throwable.printStackTrace();
                    }
                });

        Thread.sleep(3000);

        // start observing
        observer.dispose();

    }

    @Test
    public void testMayBe() throws InterruptedException {
        DisposableMaybeObserver<String> observer = Maybe.just("hello World")
                .delay(2, TimeUnit.SECONDS, Schedulers.io())
                .subscribeWith(new DisposableMaybeObserver<String>() {
                    @Override
                    public void onSuccess(String s) {
                        System.out.println(s);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("done");
                    }
                });

        Thread.sleep(3000);

        // start
        observer.dispose();
    }

    @Test
    public void testComplete() throws InterruptedException {

        DisposableCompletableObserver observer = Completable.complete()
                .delay(2, TimeUnit.SECONDS, Schedulers.io())
                .subscribeWith(new DisposableCompletableObserver() {
                    @Override
                    public void onComplete() {
                        System.out.println("done");
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    protected void onStart() {
                        System.out.println("start");
                    }
                });

        Thread.sleep(3000);

        // start
        observer.dispose();
    }

    @Test
    public void testComposite() throws InterruptedException {
        // observable
        Single<String> single = Single.just("hello jimo");

        // observer
        DisposableSingleObserver<String> observer1 = single
                .delay(2, TimeUnit.SECONDS, Schedulers.io())
                .subscribeWith(new DisposableSingleObserver<String>() {
                    @Override
                    public void onSuccess(String s) {
                        System.out.println(s);
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        throwable.printStackTrace();
                    }
                });

        DisposableMaybeObserver<String> observer2 = Maybe.just("hello World")
                .delay(2, TimeUnit.SECONDS, Schedulers.io())
                .subscribeWith(new DisposableMaybeObserver<String>() {
                    @Override
                    public void onSuccess(String s) {
                        System.out.println(s);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("done");
                    }
                });

        Thread.sleep(3000);

        CompositeDisposable disposable = new CompositeDisposable();

        disposable.add(observer1);
        disposable.add(observer2);

        // start
        disposable.dispose();
    }
}
