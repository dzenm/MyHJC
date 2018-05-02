
package android.databinding;
import com.din.myhjc.BR;
@javax.annotation.Generated("Android Data Binding")
class DataBinderMapper  {
    final static int TARGET_MIN_SDK = 21;
    public DataBinderMapper() {
    }
    public android.databinding.ViewDataBinding getDataBinder(android.databinding.DataBindingComponent bindingComponent, android.view.View view, int layoutId) {
        switch(layoutId) {
                case com.din.myhjc.R.layout.table_fragment:
                    return com.din.myhjc.databinding.TableFragmentBinding.bind(view, bindingComponent);
                case com.din.myhjc.R.layout.main_include:
                    return com.din.myhjc.databinding.MainIncludeBinding.bind(view, bindingComponent);
                case com.din.myhjc.R.layout.activity_guide:
                    return com.din.myhjc.databinding.ActivityGuideBinding.bind(view, bindingComponent);
                case com.din.myhjc.R.layout.diary_fragment:
                    return com.din.myhjc.databinding.DiaryFragmentBinding.bind(view, bindingComponent);
                case com.din.myhjc.R.layout.main_content:
                    return com.din.myhjc.databinding.MainContentBinding.bind(view, bindingComponent);
                case com.din.myhjc.R.layout.note_fragment:
                    return com.din.myhjc.databinding.NoteFragmentBinding.bind(view, bindingComponent);
                case com.din.myhjc.R.layout.activity_add:
                    return com.din.myhjc.databinding.ActivityAddBinding.bind(view, bindingComponent);
                case com.din.myhjc.R.layout.main_fragment:
                    return com.din.myhjc.databinding.MainFragmentBinding.bind(view, bindingComponent);
                case com.din.myhjc.R.layout.activity_login:
                    return com.din.myhjc.databinding.ActivityLoginBinding.bind(view, bindingComponent);
                case com.din.myhjc.R.layout.activity_profile:
                    return com.din.myhjc.databinding.ActivityProfileBinding.bind(view, bindingComponent);
                case com.din.myhjc.R.layout.activity_main:
                    return com.din.myhjc.databinding.ActivityMainBinding.bind(view, bindingComponent);
                case com.din.myhjc.R.layout.memo_fragment:
                    return com.din.myhjc.databinding.MemoFragmentBinding.bind(view, bindingComponent);
                case com.din.myhjc.R.layout.activity_register:
                    return com.din.myhjc.databinding.ActivityRegisterBinding.bind(view, bindingComponent);
                case com.din.myhjc.R.layout.activity_setting:
                    return com.din.myhjc.databinding.ActivitySettingBinding.bind(view, bindingComponent);
                case com.din.myhjc.R.layout.activity_add_note:
                    return com.din.myhjc.databinding.ActivityAddNoteBinding.bind(view, bindingComponent);
                case com.din.myhjc.R.layout.activity_start:
                    return com.din.myhjc.databinding.ActivityStartBinding.bind(view, bindingComponent);
                case com.din.myhjc.R.layout.diary_recycler:
                    return com.din.myhjc.databinding.DiaryRecyclerBinding.bind(view, bindingComponent);
        }
        return null;
    }
    android.databinding.ViewDataBinding getDataBinder(android.databinding.DataBindingComponent bindingComponent, android.view.View[] views, int layoutId) {
        switch(layoutId) {
        }
        return null;
    }
    int getLayoutId(String tag) {
        if (tag == null) {
            return 0;
        }
        final int code = tag.hashCode();
        switch(code) {
            case -334813353: {
                if(tag.equals("layout/table_fragment_0")) {
                    return com.din.myhjc.R.layout.table_fragment;
                }
                break;
            }
            case 333730008: {
                if(tag.equals("layout/main_include_0")) {
                    return com.din.myhjc.R.layout.main_include;
                }
                break;
            }
            case -206318910: {
                if(tag.equals("layout/activity_guide_0")) {
                    return com.din.myhjc.R.layout.activity_guide;
                }
                break;
            }
            case -253893566: {
                if(tag.equals("layout/diary_fragment_0")) {
                    return com.din.myhjc.R.layout.diary_fragment;
                }
                break;
            }
            case 1423540329: {
                if(tag.equals("layout/main_content_0")) {
                    return com.din.myhjc.R.layout.main_content;
                }
                break;
            }
            case -1520150647: {
                if(tag.equals("layout/note_fragment_0")) {
                    return com.din.myhjc.R.layout.note_fragment;
                }
                break;
            }
            case -1659896729: {
                if(tag.equals("layout/activity_add_0")) {
                    return com.din.myhjc.R.layout.activity_add;
                }
                break;
            }
            case -150638750: {
                if(tag.equals("layout/main_fragment_0")) {
                    return com.din.myhjc.R.layout.main_fragment;
                }
                break;
            }
            case -237232145: {
                if(tag.equals("layout/activity_login_0")) {
                    return com.din.myhjc.R.layout.activity_login;
                }
                break;
            }
            case 366786799: {
                if(tag.equals("layout/activity_profile_0")) {
                    return com.din.myhjc.R.layout.activity_profile;
                }
                break;
            }
            case 423753077: {
                if(tag.equals("layout/activity_main_0")) {
                    return com.din.myhjc.R.layout.activity_main;
                }
                break;
            }
            case 1077564737: {
                if(tag.equals("layout/memo_fragment_0")) {
                    return com.din.myhjc.R.layout.memo_fragment;
                }
                break;
            }
            case 2013163103: {
                if(tag.equals("layout/activity_register_0")) {
                    return com.din.myhjc.R.layout.activity_register;
                }
                break;
            }
            case -1398886442: {
                if(tag.equals("layout/activity_setting_0")) {
                    return com.din.myhjc.R.layout.activity_setting;
                }
                break;
            }
            case 1500484940: {
                if(tag.equals("layout/activity_add_note_0")) {
                    return com.din.myhjc.R.layout.activity_add_note;
                }
                break;
            }
            case 1818204840: {
                if(tag.equals("layout/activity_start_0")) {
                    return com.din.myhjc.R.layout.activity_start;
                }
                break;
            }
            case 1410736753: {
                if(tag.equals("layout/diary_recycler_0")) {
                    return com.din.myhjc.R.layout.diary_recycler;
                }
                break;
            }
        }
        return 0;
    }
    String convertBrIdToString(int id) {
        if (id < 0 || id >= InnerBrLookup.sKeys.length) {
            return null;
        }
        return InnerBrLookup.sKeys[id];
    }
    private static class InnerBrLookup {
        static String[] sKeys = new String[]{
            "_all"};
    }
}