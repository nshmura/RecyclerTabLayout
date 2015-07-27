package com.nshmura.recyclertablayout.demo.utils;

import com.nshmura.recyclertablayout.demo.ColorItem;
import com.nshmura.recyclertablayout.demo.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.graphics.Color;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Shinichi Nishimura on 2015/07/22.
 */
public class DemoData {

    private DemoData() {

    }

    public static List<ColorItem> loadDemoColorItems(Context context) {
        List<ColorItem> items = new ArrayList<>();

        try {
            JSONObject obj = new JSONObject(loadJSONFromAsset(context, "colors.json"));
            Iterator<String> keys = obj.keys();
            while (keys.hasNext()) {
                String key = keys.next();
                JSONObject value = obj.getJSONObject(key);
                ColorItem colorItem = new ColorItem();
                colorItem.name = value.getString("name");
                colorItem.hex = value.getString("hex");
                JSONArray rgb = value.getJSONArray("rgb");
                colorItem.color = Color.rgb(rgb.getInt(0), rgb.getInt(1), rgb.getInt(2));
                items.add(colorItem);
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return items;
    }

    public static String loadJSONFromAsset(Context context, String filename) throws IOException {
        InputStream is = context.getAssets().open(filename);
        int size = is.available();
        byte[] buffer = new byte[size];
        is.read(buffer);
        is.close();
        return new String(buffer, "UTF-8");
    }

    public static List<Integer> loadImageResourceList() {
        return new ArrayList<>(Arrays.asList(
                R.drawable.ic_3d_rotation_white_36dp,
                R.drawable.ic_accessibility_white_36dp,
                R.drawable.ic_account_balance_wallet_white_36dp,
                R.drawable.ic_account_balance_white_36dp,
                R.drawable.ic_account_box_white_36dp,
                R.drawable.ic_account_circle_white_36dp,
                R.drawable.ic_add_shopping_cart_white_36dp,
                R.drawable.ic_alarm_add_white_36dp,
                R.drawable.ic_alarm_off_white_36dp,
                R.drawable.ic_alarm_on_white_36dp,
                R.drawable.ic_alarm_white_36dp,
                R.drawable.ic_android_white_36dp,
                R.drawable.ic_announcement_white_36dp,
                R.drawable.ic_aspect_ratio_white_36dp,
                R.drawable.ic_assessment_white_36dp,
                R.drawable.ic_assignment_ind_white_36dp,
                R.drawable.ic_assignment_late_white_36dp,
                R.drawable.ic_assignment_return_white_36dp,
                R.drawable.ic_assignment_returned_white_36dp,
                R.drawable.ic_assignment_turned_in_white_36dp,
                R.drawable.ic_assignment_white_36dp,
                R.drawable.ic_autorenew_white_36dp,
                R.drawable.ic_backup_white_36dp,
                R.drawable.ic_book_white_36dp,
                R.drawable.ic_bookmark_border_white_36dp,
                R.drawable.ic_bookmark_white_36dp,
                R.drawable.ic_bug_report_white_36dp,
                R.drawable.ic_build_white_36dp,
                R.drawable.ic_cached_white_36dp,
                R.drawable.ic_camera_enhance_white_36dp,
                R.drawable.ic_card_giftcard_white_36dp,
                R.drawable.ic_card_membership_white_36dp,
                R.drawable.ic_card_travel_white_36dp,
                R.drawable.ic_change_history_white_36dp,
                R.drawable.ic_check_circle_white_36dp,
                R.drawable.ic_chrome_reader_mode_white_36dp,
                R.drawable.ic_class_white_36dp,
                R.drawable.ic_code_white_36dp,
                R.drawable.ic_credit_card_white_36dp,
                R.drawable.ic_dashboard_white_36dp,
                R.drawable.ic_delete_white_36dp,
                R.drawable.ic_description_white_36dp,
                R.drawable.ic_dns_white_36dp,
                R.drawable.ic_done_all_white_36dp,
                R.drawable.ic_done_white_36dp,
                R.drawable.ic_eject_white_36dp,
                R.drawable.ic_event_seat_white_36dp,
                R.drawable.ic_event_white_36dp,
                R.drawable.ic_exit_to_app_white_36dp,
                R.drawable.ic_explore_white_36dp,
                R.drawable.ic_extension_white_36dp,
                R.drawable.ic_face_white_36dp,
                R.drawable.ic_favorite_border_white_36dp,
                R.drawable.ic_favorite_white_36dp,
                R.drawable.ic_feedback_white_36dp,
                R.drawable.ic_find_in_page_white_36dp,
                R.drawable.ic_find_replace_white_36dp,
                R.drawable.ic_flight_land_white_36dp,
                R.drawable.ic_flight_takeoff_white_36dp,
                R.drawable.ic_flip_to_back_white_36dp,
                R.drawable.ic_flip_to_front_white_36dp,
                R.drawable.ic_get_app_white_36dp,
                R.drawable.ic_gif_white_36dp,
                R.drawable.ic_grade_white_36dp,
                R.drawable.ic_group_work_white_36dp,
                R.drawable.ic_help_outline_white_36dp,
                R.drawable.ic_help_white_36dp,
                R.drawable.ic_highlight_off_white_36dp,
                R.drawable.ic_history_white_36dp,
                R.drawable.ic_home_white_36dp,
                R.drawable.ic_hourglass_empty_white_36dp,
                R.drawable.ic_hourglass_full_white_36dp,
                R.drawable.ic_http_white_36dp,
                R.drawable.ic_https_white_36dp,
                R.drawable.ic_info_outline_white_36dp,
                R.drawable.ic_info_white_36dp,
                R.drawable.ic_input_white_36dp,
                R.drawable.ic_invert_colors_white_36dp,
                R.drawable.ic_label_outline_white_36dp,
                R.drawable.ic_label_white_36dp,
                R.drawable.ic_language_white_36dp,
                R.drawable.ic_launch_white_36dp,
                R.drawable.ic_list_white_36dp,
                R.drawable.ic_lock_open_white_36dp,
                R.drawable.ic_lock_outline_white_36dp,
                R.drawable.ic_lock_white_36dp,
                R.drawable.ic_loyalty_white_36dp,
                R.drawable.ic_markunread_mailbox_white_36dp,
                R.drawable.ic_note_add_white_36dp,
                R.drawable.ic_offline_pin_white_36dp,
                R.drawable.ic_open_in_browser_white_36dp,
                R.drawable.ic_open_in_new_white_36dp,
                R.drawable.ic_open_with_white_36dp,
                R.drawable.ic_pageview_white_36dp,
                R.drawable.ic_payment_white_36dp,
                R.drawable.ic_perm_camera_mic_white_36dp,
                R.drawable.ic_perm_contact_calendar_white_36dp,
                R.drawable.ic_perm_data_setting_white_36dp,
                R.drawable.ic_perm_device_information_white_36dp,
                R.drawable.ic_perm_identity_white_36dp,
                R.drawable.ic_perm_media_white_36dp,
                R.drawable.ic_perm_phone_msg_white_36dp,
                R.drawable.ic_perm_scan_wifi_white_36dp,
                R.drawable.ic_picture_in_picture_white_36dp,
                R.drawable.ic_play_for_work_white_36dp,
                R.drawable.ic_polymer_white_36dp,
                R.drawable.ic_power_settings_new_white_36dp,
                R.drawable.ic_print_white_36dp,
                R.drawable.ic_query_builder_white_36dp,
                R.drawable.ic_question_answer_white_36dp,
                R.drawable.ic_receipt_white_36dp,
                R.drawable.ic_redeem_white_36dp,
                R.drawable.ic_reorder_white_36dp,
                R.drawable.ic_report_problem_white_36dp,
                R.drawable.ic_restore_white_36dp,
                R.drawable.ic_room_white_36dp,
                R.drawable.ic_schedule_white_36dp,
                R.drawable.ic_search_white_36dp,
                R.drawable.ic_settings_applications_white_36dp,
                R.drawable.ic_settings_backup_restore_white_36dp,
                R.drawable.ic_settings_bluetooth_white_36dp,
                R.drawable.ic_settings_brightness_white_36dp,
                R.drawable.ic_settings_cell_white_36dp,
                R.drawable.ic_settings_ethernet_white_36dp,
                R.drawable.ic_settings_input_antenna_white_36dp,
                R.drawable.ic_settings_input_component_white_36dp,
                R.drawable.ic_settings_input_composite_white_36dp,
                R.drawable.ic_settings_input_hdmi_white_36dp,
                R.drawable.ic_settings_input_svideo_white_36dp,
                R.drawable.ic_settings_overscan_white_36dp,
                R.drawable.ic_settings_phone_white_36dp,
                R.drawable.ic_settings_power_white_36dp,
                R.drawable.ic_settings_remote_white_36dp,
                R.drawable.ic_settings_voice_white_36dp,
                R.drawable.ic_settings_white_36dp,
                R.drawable.ic_shop_two_white_36dp,
                R.drawable.ic_shop_white_36dp,
                R.drawable.ic_shopping_basket_white_36dp,
                R.drawable.ic_shopping_cart_white_36dp,
                R.drawable.ic_speaker_notes_white_36dp,
                R.drawable.ic_spellcheck_white_36dp,
                R.drawable.ic_stars_white_36dp,
                R.drawable.ic_store_white_36dp,
                R.drawable.ic_subject_white_36dp,
                R.drawable.ic_supervisor_account_white_36dp,
                R.drawable.ic_swap_horiz_white_36dp,
                R.drawable.ic_swap_vert_white_36dp,
                R.drawable.ic_swap_vertical_circle_white_36dp,
                R.drawable.ic_system_update_alt_white_36dp,
                R.drawable.ic_tab_unselected_white_36dp,
                R.drawable.ic_tab_white_36dp,
                R.drawable.ic_theaters_white_36dp,
                R.drawable.ic_thumb_down_white_36dp,
                R.drawable.ic_thumb_up_white_36dp,
                R.drawable.ic_thumbs_up_down_white_36dp,
                R.drawable.ic_toc_white_36dp,
                R.drawable.ic_today_white_36dp,
                R.drawable.ic_toll_white_36dp,
                R.drawable.ic_track_changes_white_36dp,
                R.drawable.ic_translate_white_36dp,
                R.drawable.ic_trending_down_white_36dp,
                R.drawable.ic_trending_flat_white_36dp,
                R.drawable.ic_trending_up_white_36dp,
                R.drawable.ic_turned_in_not_white_36dp,
                R.drawable.ic_turned_in_white_36dp,
                R.drawable.ic_verified_user_white_36dp,
                R.drawable.ic_view_agenda_white_36dp,
                R.drawable.ic_view_array_white_36dp,
                R.drawable.ic_view_carousel_white_36dp,
                R.drawable.ic_view_column_white_36dp,
                R.drawable.ic_view_day_white_36dp,
                R.drawable.ic_view_headline_white_36dp,
                R.drawable.ic_view_list_white_36dp,
                R.drawable.ic_view_module_white_36dp,
                R.drawable.ic_view_quilt_white_36dp,
                R.drawable.ic_view_stream_white_36dp,
                R.drawable.ic_view_week_white_36dp,
                R.drawable.ic_visibility_off_white_36dp,
                R.drawable.ic_visibility_white_36dp,
                R.drawable.ic_work_white_36dp,
                R.drawable.ic_youtube_searched_for_white_36dp,
                R.drawable.ic_zoom_in_white_36dp,
                R.drawable.ic_zoom_out_white_36dp));
    }
}
