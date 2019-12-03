package in.gov.cgg.redcrossphase1.databinding;
import in.gov.cgg.redcrossphase1.R;
import in.gov.cgg.redcrossphase1.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class ActivityTabDateBindingImpl extends ActivityTabDateBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.tabHost, 1);
        sViewsWithIds.put(android.R.id.tabs, 2);
        sViewsWithIds.put(android.R.id.tabcontent, 3);
        sViewsWithIds.put(R.id.start_date_group, 4);
        sViewsWithIds.put(R.id.datepicker_from, 5);
        sViewsWithIds.put(R.id.from_cancel, 6);
        sViewsWithIds.put(R.id.from_ok, 7);
        sViewsWithIds.put(R.id.end_date_group, 8);
        sViewsWithIds.put(R.id.datepicker_to, 9);
        sViewsWithIds.put(R.id.to_cancel, 10);
        sViewsWithIds.put(R.id.to_ok, 11);
    }
    // views
    @NonNull
    private final android.widget.LinearLayout mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ActivityTabDateBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 12, sIncludes, sViewsWithIds));
    }
    private ActivityTabDateBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.widget.DatePicker) bindings[5]
            , (android.widget.DatePicker) bindings[9]
            , (android.widget.LinearLayout) bindings[8]
            , (android.widget.Button) bindings[6]
            , (android.widget.Button) bindings[7]
            , (android.widget.LinearLayout) bindings[4]
            , (android.widget.TabHost) bindings[1]
            , (android.widget.FrameLayout) bindings[3]
            , (android.widget.TabWidget) bindings[2]
            , (android.widget.Button) bindings[10]
            , (android.widget.Button) bindings[11]
            );
        this.mboundView0 = (android.widget.LinearLayout) bindings[0];
        this.mboundView0.setTag(null);
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
    /* flag mapping
        flag 0 (0x1L): null
    flag mapping end*/
    //end
}