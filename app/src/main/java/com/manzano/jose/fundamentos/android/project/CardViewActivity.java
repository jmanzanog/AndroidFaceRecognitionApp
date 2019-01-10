/*
 * Copyright (c) 2017. Truiton (http://www.truiton.com/).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Contributors:
 * Mohit Gupt (https://github.com/mohitgupt)
 *
 */

package com.manzano.jose.fundamentos.android.project;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


public class CardViewActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private static String LOG_TAG = "CardViewActivity";
    private String jsonResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_view);
        Intent intent = getIntent();
        this.jsonResponse = intent.getStringExtra("jsonResponse");

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new MyRecyclerViewAdapter(getDataSet());
        mRecyclerView.setAdapter(mAdapter);

        // Code to Add an item with default animation
        //((MyRecyclerViewAdapter) mAdapter).addItem(obj, index);

        // Code to remove an item with default animation
        //((MyRecyclerViewAdapter) mAdapter).deleteItem(index);
    }


    @Override
    protected void onResume() {
        super.onResume();
        ((MyRecyclerViewAdapter) mAdapter).setOnItemClickListener(new MyRecyclerViewAdapter
                .MyClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                Log.i(LOG_TAG, " Clicked on Item " + position);
            }
        });
    }

    private ArrayList<DataObject> getDataSet() {
        Face[] faces = (new Gson()).fromJson(this.jsonResponse, Face[].class);
        ArrayList results = new ArrayList<DataObject>();
        DataObject obj;
        Face indexFace;
        for (int index = 0; index < faces.length; index++) {
            indexFace = faces[index];
            obj = new DataObject("Persona " + (index + 1),

                    "Edad: " +
                            String.valueOf(indexFace.getFaceAttributes().getAge().intValue()) + " años" + "\n"
                            + "Género: " + (indexFace.getFaceAttributes().getGender().equals("male") ? "Hombre" : "Mujer") + "\n"
                            + "Emoción: " + getMayorEmotion(indexFace.getFaceAttributes().getEmotion()) + "\n"
                            + "Color de pelo: " + getMayorHairColor(indexFace.getFaceAttributes().getHair().getHairColor()) + "\n"
                            + "Accesorios: " + getAccesories(indexFace.getFaceAttributes().getAccessories()) + "\n"
                            + "Maquillaje: " + "Ojos: "+(indexFace.getFaceAttributes().getMakeup().getEyeMakeup()?"SI":"NO") +  " Labios: "+(indexFace.getFaceAttributes().getMakeup().getLipMakeup()?"SI":"NO")


            );
            results.add(obj);

        }
        return results;
    }

    private String getAccesories(List<Object> objects) {
        String accesorios = "";
        if (null != objects && objects.size() > 0) {
            for (Object obj : objects) {
                if (obj instanceof LinkedTreeMap) {
                    accesorios += (((LinkedTreeMap) obj).get("type").equals("glasses") ? "Gafas" : ((LinkedTreeMap) obj).get("type")) + " ";
                }
            }
        }

        return accesorios.equals("") ? "Ninguno" : accesorios;
    }

    private String getMayorEmotion(Emotion emotion) {
        Map mapEmotion = new HashMap();
        ValueComparator bvc = new ValueComparator(mapEmotion);
        TreeMap<String, Double> sorted_map = new TreeMap<String, Double>(bvc);
        mapEmotion.put("Enfado", emotion.getAnger());
        mapEmotion.put("Desagrado", emotion.getContempt());
        mapEmotion.put("Asco", emotion.getDisgust());
        mapEmotion.put("Temor", emotion.getFear());
        mapEmotion.put("Neutral", emotion.getNeutral());
        mapEmotion.put("Tristeza", emotion.getSadness());
        mapEmotion.put("Sorpresa", emotion.getSurprise());
        sorted_map.putAll(mapEmotion);
        String selectedEmotion = sorted_map.firstKey();
        Log.d("Emociones", "results: " + sorted_map);
        return mapEmotion.get(selectedEmotion).equals(0.0) ? "Indeterminado" : selectedEmotion;

    }

    private String getMayorHairColor(List<HairColor> listHairColor) {
        Map hairColorMap = new HashMap();
        ValueComparator bvc = new ValueComparator(hairColorMap);
        TreeMap<String, Double> sorted_map = new TreeMap<String, Double>(bvc);
        for (HairColor hairColor : listHairColor) {
            hairColorMap.put(hairColor.getColor().equals("black") ? "Negro"
                            : hairColor.getColor().equals("brown") ? "Castaño"
                            : hairColor.getColor().equals("red") ? "Rojo"
                            : hairColor.getColor().equals("gray") ? "Gris" : "Indeterminado",
                    hairColor.getConfidence());
        }
        sorted_map.putAll(hairColorMap);
        String selectedColor = sorted_map.firstKey();
        Log.d("Colores", "results: " + sorted_map);
        return hairColorMap.get(selectedColor).equals(0.0) ? "Indeterminado" : selectedColor;

    }

    class ValueComparator implements Comparator<String> {
        Map<String, Double> base;

        public ValueComparator(Map<String, Double> base) {
            this.base = base;
        }

        public int compare(String a, String b) {
            if (base.get(a) >= base.get(b)) {
                return -1;
            } else {
                return 1;
            }
        }
    }
}
