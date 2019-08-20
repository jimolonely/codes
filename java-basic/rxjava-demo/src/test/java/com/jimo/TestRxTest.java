package com.jimo;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
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

    @Test
    public void createOperator() {

        // from
        final StringBuilder sb = new StringBuilder();
        Observable<String> ob1 = Observable.fromArray("1", "2", "3", "4");
        Disposable disposable = ob1.map(s -> s + "0")
                .subscribe(sb::append);
        System.out.println(sb);

        Observable<Long> timer = Observable.timer(2, TimeUnit.SECONDS);
    }

    @Test
    public void combine() {

        Integer[] numbers = {1, 2, 3, 4, 5};
        String[] letters = {"a", "b", "c", "d"};

        Observable<Integer> ob1 = Observable.fromArray(numbers);
        Observable<String> ob2 = Observable.fromArray(letters);
        Observable.combineLatest(ob1, ob2, (a, b) -> a + b)
                .subscribe(System.out::println);
    }
}
