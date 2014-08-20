package me.mattlogan.surgepricewidget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import me.mattlogan.surgepricewidget.api.model.Price;

public class PriceListAdapter extends BaseAdapter {

    Context context;
    List<Price> priceList;

    public PriceListAdapter(Context context) {
        this.context = context;
    }

    public void setPriceList(List<Price> priceList) {
        this.priceList = priceList;
    }

    @Override public int getCount() {
        if (priceList != null) {
            return priceList.size();
        } else {
            return 0;
        }
    }

    @Override public Object getItem(int i) {
        if (priceList != null) {
            return priceList.get(i);
        } else {
            return 0;
        }
    }

    @Override public long getItemId(int i) {
        return 0;
    }

    static class ViewHolder {
        TextView carNameText;
        TextView surgePriceText;
    }

    @Override public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        if (convertView == null) {
            LayoutInflater li = (LayoutInflater)
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = li.inflate(R.layout.price_list_item, parent, false);

            vh = new ViewHolder();
            vh.carNameText = (TextView) convertView.findViewById(R.id.car_name_text);
            vh.surgePriceText = (TextView) convertView.findViewById(R.id.surge_price_text);
            convertView.setTag(vh);
        }

        vh = (ViewHolder) convertView.getTag();

        vh.carNameText.setText(priceList.get(position).getLocalizedDisplayName());
        vh.surgePriceText.setText("" + priceList.get(position).getSurgeMultiplier());

        return convertView;
    }
}
