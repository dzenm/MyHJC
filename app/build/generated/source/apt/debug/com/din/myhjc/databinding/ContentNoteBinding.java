package com.din.myhjc.databinding;
import com.din.myhjc.R;
import com.din.myhjc.BR;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
@javax.annotation.Generated("Android Data Binding")
public class ContentNoteBinding extends android.databinding.ViewDataBinding  {

    @Nullable
    private static final android.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.appBar, 1);
        sViewsWithIds.put(R.id.collapsing, 2);
        sViewsWithIds.put(R.id.imageView, 3);
        sViewsWithIds.put(R.id.toolbar, 4);
        sViewsWithIds.put(R.id.recyclerView, 5);
        sViewsWithIds.put(R.id.floatButton, 6);
    }
    // views
    @NonNull
    public final android.support.design.widget.AppBarLayout appBar;
    @NonNull
    public final android.support.design.widget.CollapsingToolbarLayout collapsing;
    @NonNull
    public final android.support.design.widget.FloatingActionButton floatButton;
    @NonNull
    public final android.widget.ImageView imageView;
    @NonNull
    public final android.support.design.widget.CoordinatorLayout note;
    @NonNull
    public final android.support.v7.widget.RecyclerView recyclerView;
    @NonNull
    public final android.support.v7.widget.Toolbar toolbar;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ContentNoteBinding(@NonNull android.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        super(bindingComponent, root, 0);
        final Object[] bindings = mapBindings(bindingComponent, root, 7, sIncludes, sViewsWithIds);
        this.appBar = (android.support.design.widget.AppBarLayout) bindings[1];
        this.collapsing = (android.support.design.widget.CollapsingToolbarLayout) bindings[2];
        this.floatButton = (android.support.design.widget.FloatingActionButton) bindings[6];
        this.imageView = (android.widget.ImageView) bindings[3];
        this.note = (android.support.design.widget.CoordinatorLayout) bindings[0];
        this.note.setTag(null);
        this.recyclerView = (android.support.v7.widget.RecyclerView) bindings[5];
        this.toolbar = (android.support.v7.widget.Toolbar) bindings[4];
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
    public static ContentNoteBinding inflate(@NonNull android.view.LayoutInflater inflater, @Nullable android.view.ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    @NonNull
    public static ContentNoteBinding inflate(@NonNull android.view.LayoutInflater inflater, @Nullable android.view.ViewGroup root, boolean attachToRoot, @Nullable android.databinding.DataBindingComponent bindingComponent) {
        return android.databinding.DataBindingUtil.<ContentNoteBinding>inflate(inflater, com.din.myhjc.R.layout.content_note, root, attachToRoot, bindingComponent);
    }
    @NonNull
    public static ContentNoteBinding inflate(@NonNull android.view.LayoutInflater inflater) {
        return inflate(inflater, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    @NonNull
    public static ContentNoteBinding inflate(@NonNull android.view.LayoutInflater inflater, @Nullable android.databinding.DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(com.din.myhjc.R.layout.content_note, null, false), bindingComponent);
    }
    @NonNull
    public static ContentNoteBinding bind(@NonNull android.view.View view) {
        return bind(view, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    @NonNull
    public static ContentNoteBinding bind(@NonNull android.view.View view, @Nullable android.databinding.DataBindingComponent bindingComponent) {
        if (!"layout/content_note_0".equals(view.getTag())) {
            throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
        }
        return new ContentNoteBinding(bindingComponent, view);
    }
    /* flag mapping
        flag 0 (0x1L): null
    flag mapping end*/
    //end
}