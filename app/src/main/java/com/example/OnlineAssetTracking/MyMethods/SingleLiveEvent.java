package com.example.OnlineAssetTracking.MyMethods;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import org.jetbrains.annotations.Nullable;

import java.util.concurrent.atomic.AtomicBoolean;

import kotlin.jvm.internal.Intrinsics;

public class SingleLiveEvent<T> extends MutableLiveData<T> {
        private final AtomicBoolean pending = new AtomicBoolean(false);

        public void observe(@NonNull LifecycleOwner owner, @NonNull Observer<? super T> observer) {
                Intrinsics.checkNotNullParameter(owner, "owner");
                Intrinsics.checkNotNullParameter(observer, "observer");
                super.observe(owner, (t -> {
                        if (SingleLiveEvent.this.pending.compareAndSet(true, false)) {
                                observer.onChanged(t);
                        }

                }));
        }

        public void setValue(@Nullable Object t) {
                this.pending.set(true);
                super.setValue((T) t);
        }
        public void postValue(@Nullable Object t) {
                this.pending.set(true);
                super.postValue((T) t);
        }

        public final void call() {
                this.postValue((T) null);
        }
        }
