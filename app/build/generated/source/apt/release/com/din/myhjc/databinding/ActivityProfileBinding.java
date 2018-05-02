package com.din.myhjc.databinding;
import com.din.myhjc.R;
import com.din.myhjc.BR;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
@javax.annotation.Generated("Android Data Binding")
public class ActivityProfileBinding extends android.databinding.ViewDataBinding  {

    @Nullable
    private static final android.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.toolbar, 1);
        sViewsWithIds.put(R.id.headBackGround, 2);
        sViewsWithIds.put(R.id.headPhoto, 3);
        sViewsWithIds.put(R.id.username, 4);
        sViewsWithIds.put(R.id.userText, 5);
        sViewsWithIds.put(R.id.email, 6);
        sViewsWithIds.put(R.id.emailText, 7);
        sViewsWithIds.put(R.id.phone, 8);
        sViewsWithIds.put(R.id.phoneText, 9);
        sViewsWithIds.put(R.id.changePassword, 10);
    }
    // views
    @NonNull
    public final android.widget.RelativeLayout changePassword;
    @NonNull
    public final android.widget.RelativeLayout email;
    @NonNull
    public final android.widget.TextView emailText;
    @NonNull
    public final android.widget.RelativeLayout headBackGround;
    @NonNull
    public final android.widget.ImageView headPhoto;
    @NonNull
    private final android.widget.RelativeLayout mboundView0;
    @NonNull
    public final android.widget.RelativeLayout phone;
    @NonNull
    public final android.widget.TextView phoneText;
    @NonNull
    public final android.support.v7.widget.Toolbar toolbar;
    @NonNull
    public final android.widget.TextView userText;
    @NonNull
    public final android.widget.RelativeLayout username;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ActivityProfileBinding(@NonNull android.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        super(bindingComponent, root, 0);
        final Object[] bindings = mapBindings(bindingComponent, root, 11, sIncludes, sViewsWithIds);
        this.changePassword = (android.widget.RelativeLayout) bindings[10];
        this.email = (android.widget.RelativeLayout) bindings[6];
        this.emailText = (android.widget.TextView) bindings[7];
        this.headBackGround = (android.widget.RelativeLayout) bindings[2];
        this.headPhoto = (android.widget.ImageView) bindings[3];
        this.mboundView0 = (android.widget.RelativeLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.phone = (android.widget.RelativeLayout) bindings[8];
        this.phoneText = (android.widget.TextView) bindings[9];
        this.toolbar = (android.support.v7.widget.Toolbar) bindings[1];
        this.userText = (android.widget.TextView) bindings[5];
        this.username = (android.widget.RelativeLayout) bindings[4];
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
    public static ActivityProfileBinding inflate(@NonNull android.view.LayoutInflater inflater, @Nullable android.view.ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    @NonNull
    public static ActivityProfileBinding inflate(@NonNull android.view.LayoutInflater inflater, @Nullable android.view.ViewGroup root, boolean attachToRoot, @Nullable android.databinding.DataBindingComponent bindingComponent) {
        return android.databinding.DataBindingUtil.<ActivityProfileBinding>inflate(inflater, com.din.myhjc.R.layout.activity_profile, root, attachToRoot, bindingComponent);
    }
    @NonNull
    public static ActivityProfileBinding inflate(@NonNull android.view.LayoutInflater inflater) {
        return inflate(inflater, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    @NonNull
    public static ActivityProfileBinding inflate(@NonNull android.view.LayoutInflater inflater, @Nullable android.databinding.DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(com.din.myhjc.R.layout.activity_profile, null, false), bindingComponent);
    }
    @NonNull
    public static ActivityProfileBinding bind(@NonNull android.view.View view) {
        return bind(view, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    @NonNull
    public static ActivityProfileBinding bind(@NonNull android.view.View view, @Nullable android.databinding.DataBindingComponent bindingComponent) {
        if (!"layout/activity_profile_0".equals(view.getTag())) {
            throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
        }
        return new ActivityProfileBinding(bindingComponent, view);
    }
    /* flag mapping
        flag 0 (0x1L): null
    flag mapping end*/
    //end
}