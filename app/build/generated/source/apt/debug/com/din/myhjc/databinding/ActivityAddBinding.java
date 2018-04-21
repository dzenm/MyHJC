package com.din.myhjc.databinding;
import com.din.myhjc.R;
import com.din.myhjc.BR;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
@javax.annotation.Generated("Android Data Binding")
public class ActivityAddBinding extends android.databinding.ViewDataBinding  {

    @Nullable
    private static final android.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.appBar, 1);
        sViewsWithIds.put(R.id.collapsing_toobar, 2);
        sViewsWithIds.put(R.id.image, 3);
        sViewsWithIds.put(R.id.toolbar, 4);
        sViewsWithIds.put(R.id.back, 5);
        sViewsWithIds.put(R.id.date, 6);
        sViewsWithIds.put(R.id.confirm, 7);
        sViewsWithIds.put(R.id.content, 8);
        sViewsWithIds.put(R.id.weather, 9);
        sViewsWithIds.put(R.id.mood, 10);
        sViewsWithIds.put(R.id.location, 11);
        sViewsWithIds.put(R.id.view, 12);
        sViewsWithIds.put(R.id.recyclerView, 13);
    }
    // views
    @NonNull
    public final android.support.design.widget.AppBarLayout appBar;
    @NonNull
    public final android.widget.ImageButton back;
    @NonNull
    public final android.support.design.widget.CollapsingToolbarLayout collapsingToobar;
    @NonNull
    public final android.widget.ImageButton confirm;
    @NonNull
    public final android.widget.EditText content;
    @NonNull
    public final android.widget.TextView date;
    @NonNull
    public final android.widget.ImageView image;
    @NonNull
    public final android.widget.TextView location;
    @NonNull
    private final android.support.design.widget.CoordinatorLayout mboundView0;
    @NonNull
    public final android.widget.TextView mood;
    @NonNull
    public final android.support.v7.widget.RecyclerView recyclerView;
    @NonNull
    public final android.support.v7.widget.Toolbar toolbar;
    @NonNull
    public final android.view.View view;
    @NonNull
    public final android.widget.TextView weather;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ActivityAddBinding(@NonNull android.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        super(bindingComponent, root, 0);
        final Object[] bindings = mapBindings(bindingComponent, root, 14, sIncludes, sViewsWithIds);
        this.appBar = (android.support.design.widget.AppBarLayout) bindings[1];
        this.back = (android.widget.ImageButton) bindings[5];
        this.collapsingToobar = (android.support.design.widget.CollapsingToolbarLayout) bindings[2];
        this.confirm = (android.widget.ImageButton) bindings[7];
        this.content = (android.widget.EditText) bindings[8];
        this.date = (android.widget.TextView) bindings[6];
        this.image = (android.widget.ImageView) bindings[3];
        this.location = (android.widget.TextView) bindings[11];
        this.mboundView0 = (android.support.design.widget.CoordinatorLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.mood = (android.widget.TextView) bindings[10];
        this.recyclerView = (android.support.v7.widget.RecyclerView) bindings[13];
        this.toolbar = (android.support.v7.widget.Toolbar) bindings[4];
        this.view = (android.view.View) bindings[12];
        this.weather = (android.widget.TextView) bindings[9];
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
    public static ActivityAddBinding inflate(@NonNull android.view.LayoutInflater inflater, @Nullable android.view.ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    @NonNull
    public static ActivityAddBinding inflate(@NonNull android.view.LayoutInflater inflater, @Nullable android.view.ViewGroup root, boolean attachToRoot, @Nullable android.databinding.DataBindingComponent bindingComponent) {
        return android.databinding.DataBindingUtil.<ActivityAddBinding>inflate(inflater, com.din.myhjc.R.layout.activity_add, root, attachToRoot, bindingComponent);
    }
    @NonNull
    public static ActivityAddBinding inflate(@NonNull android.view.LayoutInflater inflater) {
        return inflate(inflater, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    @NonNull
    public static ActivityAddBinding inflate(@NonNull android.view.LayoutInflater inflater, @Nullable android.databinding.DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(com.din.myhjc.R.layout.activity_add, null, false), bindingComponent);
    }
    @NonNull
    public static ActivityAddBinding bind(@NonNull android.view.View view) {
        return bind(view, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    @NonNull
    public static ActivityAddBinding bind(@NonNull android.view.View view, @Nullable android.databinding.DataBindingComponent bindingComponent) {
        if (!"layout/activity_add_0".equals(view.getTag())) {
            throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
        }
        return new ActivityAddBinding(bindingComponent, view);
    }
    /* flag mapping
        flag 0 (0x1L): null
    flag mapping end*/
    //end
}