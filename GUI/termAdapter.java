package android.C868.Capstone.All.GUI;

import android.content.Context;
import android.content.Intent;
import android.C868.Capstone.All.Model.Term;
import android.C868.Capstone.R;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class termAdapter extends RecyclerView.Adapter<termAdapter.TermViewHolder> implements Filterable {

    class TermViewHolder extends RecyclerView.ViewHolder {

        private final TextView rowItemTerm;
        private TermViewHolder(View itemView) {
            super(itemView);
            rowItemTerm = itemView.findViewById(R.id.rowItem_term);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    final Term current = mTerms.get(position);
                    Intent intent = new Intent(context, termDetail.class);
                    intent.putExtra("id", current.getTermID());
                    intent.putExtra("name", current.getTermName());
                    intent.putExtra("start", current.getTermStart());
                    intent.putExtra("end", current.getTermEnd());
                    intent.putExtra("created", current.getCreated_date());
                    context.startActivity(intent);
                }
            });
        }
    }

    private List<Term> mTerms;
    private final List<Term> mTermsList;
    private final Context context;
    private final LayoutInflater mInflater;

    public termAdapter(Context context, List<Term> mTerms){

        mInflater = LayoutInflater.from(context);
        this.context = context;
        this.mTerms = mTerms;
        mTermsList = new ArrayList<>(mTerms);

    }

    @NonNull
    @Override
    public termAdapter.TermViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = mInflater.inflate(R.layout.term_item_row, parent, false);

        return new TermViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull termAdapter.TermViewHolder holder, int position) {

        if (mTerms != null) {

            Term current = mTerms.get(position);
            holder.rowItemTerm.setText(current.getTermName());

        }

        else {

            holder.rowItemTerm.setText(R.string.empty_noTermsAvailable);

        }
    }

    public void setTerms(List<Term> terms) {
        mTerms = terms;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mTerms.size();
    }
    @Override
    public Filter getFilter() {
        return FilterTerms;
    }

    private final Filter FilterTerms = new Filter() {

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {

            String searchText = charSequence.toString().toLowerCase();
            List<Term> tempList = new ArrayList<>();
            if (searchText.length() == 0 | searchText.isEmpty()) {
                tempList.addAll(mTermsList);
            }
            else {
                for (Term item : mTermsList) {
                    if (item.getTermName().toLowerCase().contains(searchText)) {
                        tempList.add(item);
                    }
                }
            }

            FilterResults filterResults = new FilterResults();
            filterResults.values = tempList;

            return filterResults;

        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

            mTerms.clear();
            mTerms.addAll((Collection<? extends Term>) filterResults.values);
            notifyDataSetChanged();

        }
    };
}
