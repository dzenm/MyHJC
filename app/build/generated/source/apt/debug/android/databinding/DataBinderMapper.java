
package android.databinding;
import com.din.myhjc.BR;
@javax.annotation.Generated("Android Data Binding")
class DataBinderMapper  {
    final static int TARGET_MIN_SDK = 21;
    public DataBinderMapper() {
    }
    public android.databinding.ViewDataBinding getDataBinder(android.databinding.DataBindingComponent bindingComponent, android.view.View view, int layoutId) {
        switch(layoutId) {
                case com.din.myhjc.R.layout.drawer_main:
                    return com.din.myhjc.databinding.DrawerMainBinding.bind(view, bindingComponent);
                case com.din.myhjc.R.layout.activity_guide:
                    return com.din.myhjc.databinding.ActivityGuideBinding.bind(view, bindingComponent);
                case com.din.myhjc.R.layout.diary_fragment:
                    return com.din.myhjc.databinding.DiaryFragmentBinding.bind(view, bindingComponent);
                case com.din.myhjc.R.layout.content_note:
                    return com.din.myhjc.databinding.ContentNoteBinding.bind(view, bindingComponent);
                case com.din.myhjc.R.layout.activity_add:
                    return com.din.myhjc.databinding.ActivityAddBinding.bind(view, bindingComponent);
                case com.din.myhjc.R.layout.content_main:
                    return com.din.myhjc.databinding.ContentMainBinding.bind(view, bindingComponent);
                case com.din.myhjc.R.layout.activity_login:
                    return com.din.myhjc.databinding.ActivityLoginBinding.bind(view, bindingComponent);
                case com.din.myhjc.R.layout.content_memo:
                    return com.din.myhjc.databinding.ContentMemoBinding.bind(view, bindingComponent);
                case com.din.myhjc.R.layout.activity_main:
                    return com.din.myhjc.databinding.ActivityMainBinding.bind(view, bindingComponent);
                case com.din.myhjc.R.layout.content_table:
                    return com.din.myhjc.databinding.ContentTableBinding.bind(view, bindingComponent);
                case com.din.myhjc.R.layout.activity_register:
                    return com.din.myhjc.databinding.ActivityRegisterBinding.bind(view, bindingComponent);
                case com.din.myhjc.R.layout.activity_setting:
                    return com.din.myhjc.databinding.ActivitySettingBinding.bind(view, bindingComponent);
                case com.din.myhjc.R.layout.activity_add_note:
                    return com.din.myhjc.databinding.ActivityAddNoteBinding.bind(view, bindingComponent);
                case com.din.myhjc.R.layout.activity_start:
                    return com.din.myhjc.databinding.ActivityStartBinding.bind(view, bindingComponent);
                case com.din.myhjc.R.layout.content_diary:
                    return com.din.myhjc.databinding.ContentDiaryBinding.bind(view, bindingComponent);
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
            case -2104297453: {
                if(tag.equals("layout/drawer_main_0")) {
                    return com.din.myhjc.R.layout.drawer_main;
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
            case 772969262: {
                if(tag.equals("layout/content_note_0")) {
                    return com.din.myhjc.R.layout.content_note;
                }
                break;
            }
            case -1659896729: {
                if(tag.equals("layout/activity_add_0")) {
                    return com.din.myhjc.R.layout.activity_add;
                }
                break;
            }
            case 731091765: {
                if(tag.equals("layout/content_main_0")) {
                    return com.din.myhjc.R.layout.content_main;
                }
                break;
            }
            case -237232145: {
                if(tag.equals("layout/activity_login_0")) {
                    return com.din.myhjc.R.layout.activity_login;
                }
                break;
            }
            case 734905974: {
                if(tag.equals("layout/content_memo_0")) {
                    return com.din.myhjc.R.layout.content_memo;
                }
                break;
            }
            case 423753077: {
                if(tag.equals("layout/activity_main_0")) {
                    return com.din.myhjc.R.layout.activity_main;
                }
                break;
            }
            case -1194917548: {
                if(tag.equals("layout/content_table_0")) {
                    return com.din.myhjc.R.layout.content_table;
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
            case 2013200393: {
                if(tag.equals("layout/content_diary_0")) {
                    return com.din.myhjc.R.layout.content_diary;
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