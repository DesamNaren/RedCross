package in.gov.cgg.redcrossphase1.databinding;
import in.gov.cgg.redcrossphase1.R;
import in.gov.cgg.redcrossphase1.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class DrilldownAdapterBindingImpl extends DrilldownAdapterBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.tv_headerdistrcit, 1);
        sViewsWithIds.put(R.id.tv_district, 2);
        sViewsWithIds.put(R.id.tv_headermandal, 3);
        sViewsWithIds.put(R.id.tv_mandal, 4);
        sViewsWithIds.put(R.id.tv_headervillage, 5);
        sViewsWithIds.put(R.id.tv_village, 6);
        sViewsWithIds.put(R.id.tv_headermeberId, 7);
        sViewsWithIds.put(R.id.tv_memberId, 8);
        sViewsWithIds.put(R.id.tv_headername, 9);
        sViewsWithIds.put(R.id.tv_name, 10);
        sViewsWithIds.put(R.id.tv_headergender, 11);
        sViewsWithIds.put(R.id.tv_gender, 12);
        sViewsWithIds.put(R.id.tv_headerdob, 13);
        sViewsWithIds.put(R.id.tv_do, 14);
        sViewsWithIds.put(R.id.tv_headermobile, 15);
        sViewsWithIds.put(R.id.tv_mobilenumber, 16);
        sViewsWithIds.put(R.id.tv_headerbloodgroup, 17);
        sViewsWithIds.put(R.id.tv_bloodgroup, 18);
        sViewsWithIds.put(R.id.tv_headeremail, 19);
        sViewsWithIds.put(R.id.tv_email, 20);
        sViewsWithIds.put(R.id.tv_headerclass, 21);
        sViewsWithIds.put(R.id.tv_class, 22);
        sViewsWithIds.put(R.id.tv_headerinstutename, 23);
        sViewsWithIds.put(R.id.tv_instaname, 24);
        sViewsWithIds.put(R.id.tv_headerinstutetype, 25);
        sViewsWithIds.put(R.id.tv_instatype, 26);
        sViewsWithIds.put(R.id.tv_headerenddate, 27);
        sViewsWithIds.put(R.id.tv_enddate, 28);
    }
    // views
    @NonNull
    private final android.widget.LinearLayout mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public DrilldownAdapterBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 29, sIncludes, sViewsWithIds));
    }
    private DrilldownAdapterBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.widget.TextView) bindings[18]
            , (android.widget.TextView) bindings[22]
            , (android.widget.TextView) bindings[2]
            , (android.widget.TextView) bindings[14]
            , (android.widget.TextView) bindings[20]
            , (android.widget.TextView) bindings[28]
            , (android.widget.TextView) bindings[12]
            , (android.widget.TextView) bindings[17]
            , (android.widget.TextView) bindings[21]
            , (android.widget.TextView) bindings[1]
            , (android.widget.TextView) bindings[13]
            , (android.widget.TextView) bindings[19]
            , (android.widget.TextView) bindings[27]
            , (android.widget.TextView) bindings[11]
            , (android.widget.TextView) bindings[23]
            , (android.widget.TextView) bindings[25]
            , (android.widget.TextView) bindings[3]
            , (android.widget.TextView) bindings[7]
            , (android.widget.TextView) bindings[15]
            , (android.widget.TextView) bindings[9]
            , (android.widget.TextView) bindings[5]
            , (android.widget.TextView) bindings[24]
            , (android.widget.TextView) bindings[26]
            , (android.widget.TextView) bindings[4]
            , (android.widget.TextView) bindings[8]
            , (android.widget.TextView) bindings[16]
            , (android.widget.TextView) bindings[10]
            , (android.widget.TextView) bindings[6]
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