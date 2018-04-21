package com.din.myhjc.databinding;
import com.din.myhjc.R;
import com.din.myhjc.BR;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
@javax.annotation.Generated("Android Data Binding")
public class ActivityAddNoteBinding extends android.databinding.ViewDataBinding  {

    @Nullable
    private static final android.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.toolbar, 1);
        sViewsWithIds.put(R.id.back, 2);
        sViewsWithIds.put(R.id.textTitle, 3);
        sViewsWithIds.put(R.id.confirm, 4);
        sViewsWithIds.put(R.id.title, 5);
        sViewsWithIds.put(R.id.content, 6);
        sViewsWithIds.put(R.id.bottom, 7);
    }
    // views
    @NonNull
    public final android.widget.ImageButton back;
    @NonNull
    public final android.view.View bottom;
    @NonNull
    public final android.widget.ImageButton confirm;
    @NonNull
    public final com.din.myhjc.utils.StationeryEditText content;
    @NonNull
    public final android.widget.RelativeLayout root;
    @NonNull
    public final android.widget.TextView textTitle;
    @NonNull
    public final com.din.myhjc.utils.ClearEditText title;
    @NonNull
    public final android.support.v7.widget.Toolbar toolbar;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ActivityAddNoteBinding(@NonNull android.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        super(bindingComponent, root, 0);
        final Object[] bindings = mapBindings(bindingComponent, root, 8, sIncludes, sViewsWithIds);
        this.back = (android.widget.ImageButton) bindings[2];
        this.bottom = (android.view.View) bindings[7];
        this.confirm = (android.widget.ImageButton) bindings[4];
        this.content = (com.din.myhjc.utils.StationeryEditText) bindings[6];
        this.root = (android.widget.RelativeLayout) bindings[0];
        this.root.setTag(null);
        this.textTitle = (android.widget.TextView) bindings[3];
        this.title = (com.din.myhjc.utils.ClearEditText) bindings[5];
        this.toolbar = (android.support.v7.widget.Toolbar) bindings[1];
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
    public static ActivityAddNoteBinding inflate(@NonNull android.view.LayoutInflater inflater, @Nullable android.view.ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    @NonNull
    public static ActivityAddNoteBinding inflate(@NonNull android.view.LayoutInflater inflater, @Nullable android.view.ViewGroup root, boolean attachToRoot, @Nullable android.databinding.DataBindingComponent bindingComponent) {
        return android.databinding.DataBindingUtil.<ActivityAddNoteBinding>inflate(inflater, com.din.myhjc.R.layout.activity_add_note, root, attachToRoot, bindingComponent);
    }
    @NonNull
    public static ActivityAddNoteBinding inflate(@NonNull android.view.LayoutInflater inflater) {
        return inflate(inflater, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    @NonNull
    public static ActivityAddNoteBinding inflate(@NonNull android.view.LayoutInflater inflater, @Nullable android.databinding.DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(com.din.myhjc.R.layout.activity_add_note, null, false), bindingComponent);
    }
    @NonNull
    public static ActivityAddNoteBinding bind(@NonNull android.view.View view) {
        return bind(view, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    @NonNull
    public static ActivityAddNoteBinding bind(@NonNull android.view.View view, @Nullable android.databinding.DataBindingComponent bindingComponent) {
        if (!"layout/activity_add_note_0".equals(view.getTag())) {
            throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
        }
        return new ActivityAddNoteBinding(bindingComponent, view);
    }
    /* flag mapping
        flag 0 (0x1L): null
    flag mapping end*/
    //end
}