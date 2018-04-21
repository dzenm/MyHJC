package com.din.myhjc.databinding;
import com.din.myhjc.R;
import com.din.myhjc.BR;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
@javax.annotation.Generated("Android Data Binding")
public class ActivityRegisterBinding extends android.databinding.ViewDataBinding  {

    @Nullable
    private static final android.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.toolbar, 1);
        sViewsWithIds.put(R.id.toolbarText, 2);
        sViewsWithIds.put(R.id.setHeadPhoto, 3);
        sViewsWithIds.put(R.id.content, 4);
        sViewsWithIds.put(R.id.user, 5);
        sViewsWithIds.put(R.id.password, 6);
        sViewsWithIds.put(R.id.phone, 7);
        sViewsWithIds.put(R.id.email, 8);
        sViewsWithIds.put(R.id.register, 9);
        sViewsWithIds.put(R.id.animalView, 10);
    }
    // views
    @NonNull
    public final com.din.myhjc.utils.TransitionView animalView;
    @NonNull
    public final android.widget.LinearLayout content;
    @NonNull
    public final com.din.myhjc.utils.ClearEditText email;
    @NonNull
    private final android.widget.RelativeLayout mboundView0;
    @NonNull
    public final com.din.myhjc.utils.ClearEditText password;
    @NonNull
    public final com.din.myhjc.utils.ClearEditText phone;
    @NonNull
    public final android.widget.TextView register;
    @NonNull
    public final android.widget.ImageView setHeadPhoto;
    @NonNull
    public final android.support.v7.widget.Toolbar toolbar;
    @NonNull
    public final android.widget.TextView toolbarText;
    @NonNull
    public final com.din.myhjc.utils.ClearEditText user;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ActivityRegisterBinding(@NonNull android.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        super(bindingComponent, root, 0);
        final Object[] bindings = mapBindings(bindingComponent, root, 11, sIncludes, sViewsWithIds);
        this.animalView = (com.din.myhjc.utils.TransitionView) bindings[10];
        this.content = (android.widget.LinearLayout) bindings[4];
        this.email = (com.din.myhjc.utils.ClearEditText) bindings[8];
        this.mboundView0 = (android.widget.RelativeLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.password = (com.din.myhjc.utils.ClearEditText) bindings[6];
        this.phone = (com.din.myhjc.utils.ClearEditText) bindings[7];
        this.register = (android.widget.TextView) bindings[9];
        this.setHeadPhoto = (android.widget.ImageView) bindings[3];
        this.toolbar = (android.support.v7.widget.Toolbar) bindings[1];
        this.toolbarText = (android.widget.TextView) bindings[2];
        this.user = (com.din.myhjc.utils.ClearEditText) bindings[5];
        setRootTag(root);
        // listeners
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x1L;
        }
        requestRebind();
    }

    @Override
    public boolean hasPendingBindings() {
        synchronized(this) {
            if (mDirtyFlags != 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean setVariable(int variableId, @Nullable Object variable)  {
        boolean variableSet = true;
            return variableSet;
    }

    @Override
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
        }
        return false;
    }

    @Override
    protected void executeBindings() {
        long dirtyFlags = 0;
        synchronized(this) {
            dirtyFlags = mDirtyFlags;
            mDirtyFlags = 0;
        }
        // batch finished
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;

    @NonNull
    public static ActivityRegisterBinding inflate(@NonNull android.view.LayoutInflater inflater, @Nullable android.view.ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    @NonNull
    public static ActivityRegisterBinding inflate(@NonNull android.view.LayoutInflater inflater, @Nullable android.view.ViewGroup root, boolean attachToRoot, @Nullable android.databinding.DataBindingComponent bindingComponent) {
        return android.databinding.DataBindingUtil.<ActivityRegisterBinding>inflate(inflater, com.din.myhjc.R.layout.activity_register, root, attachToRoot, bindingComponent);
    }
    @NonNull
    public static ActivityRegisterBinding inflate(@NonNull android.view.LayoutInflater inflater) {
        return inflate(inflater, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    @NonNull
    public static ActivityRegisterBinding inflate(@NonNull android.view.LayoutInflater inflater, @Nullable android.databinding.DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(com.din.myhjc.R.layout.activity_register, null, false), bindingComponent);
    }
    @NonNull
    public static ActivityRegisterBinding bind(@NonNull android.view.View view) {
        return bind(view, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    @NonNull
    public static ActivityRegisterBinding bind(@NonNull android.view.View view, @Nullable android.databinding.DataBindingComponent bindingComponent) {
        if (!"layout/activity_register_0".equals(view.getTag())) {
            throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
        }
        return new ActivityRegisterBinding(bindingComponent, view);
    }
    /* flag mapping
        flag 0 (0x1L): null
    flag mapping end*/
    //end
}