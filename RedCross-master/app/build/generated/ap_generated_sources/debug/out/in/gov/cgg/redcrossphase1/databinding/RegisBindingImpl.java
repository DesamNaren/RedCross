package in.gov.cgg.redcrossphase1.databinding;
import in.gov.cgg.redcrossphase1.R;
import in.gov.cgg.redcrossphase1.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class RegisBindingImpl extends RegisBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.virtuo_logo, 1);
        sViewsWithIds.put(R.id.cv_main, 2);
        sViewsWithIds.put(R.id.tv_signUp, 3);
        sViewsWithIds.put(R.id.ll_userDetails, 4);
        sViewsWithIds.put(R.id.name_text_input, 5);
        sViewsWithIds.put(R.id.name_edit_text, 6);
        sViewsWithIds.put(R.id.mobile_text_input, 7);
        sViewsWithIds.put(R.id.mobile_edit_text, 8);
        sViewsWithIds.put(R.id.email_text_input, 9);
        sViewsWithIds.put(R.id.email_edit_text, 10);
        sViewsWithIds.put(R.id.ll_register, 11);
        sViewsWithIds.put(R.id.btn_register, 12);
        sViewsWithIds.put(R.id.img_footer, 13);
    }
    // views
    @NonNull
    private final android.widget.RelativeLayout mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public RegisBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 14, sIncludes, sViewsWithIds));
    }
    private RegisBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.widget.Button) bindings[12]
            , (androidx.cardview.widget.CardView) bindings[2]
            , (com.google.android.material.textfield.TextInputEditText) bindings[10]
            , (com.google.android.material.textfield.TextInputLayout) bindings[9]
            , (android.widget.ImageView) bindings[13]
            , (android.widget.LinearLayout) bindings[11]
            , (android.widget.LinearLayout) bindings[4]
            , (com.google.android.material.textfield.TextInputEditText) bindings[8]
            , (com.google.android.material.textfield.TextInputLayout) bindings[7]
            , (com.google.android.material.textfield.TextInputEditText) bindings[6]
            , (com.google.android.material.textfield.TextInputLayout) bindings[5]
            , (android.widget.TextView) bindings[3]
            , (android.widget.ImageView) bindings[1]
            );
        this.mboundView0 = (android.widget.RelativeLayout) bindings[0];
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