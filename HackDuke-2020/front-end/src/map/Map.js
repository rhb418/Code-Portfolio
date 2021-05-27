import React from "react";
import {
  GoogleMap,
  InfoBox,
  Marker,
  useLoadScript,
} from "@react-google-maps/api";
import { makeStyles, CircularProgress } from "@material-ui/core";

import ResultBox from "../ResultBox";
import MyMapContext from "./MyMapContext";
import SearchMap from "./search/SearchMap";
import MyInfoBox from "./infobox/MyInfoBox";
import mapStyles from "./mapStyles";
import { openSnackbarExternal } from "../snackbar/Notifier";

const useStyles = makeStyles((theme) => ({
  root: {
    display: "flex",
  },
  loadingContainer: {
    width: "100vw",
    height: "100vh",
    display: "flex",
    alignItems: "center",
    justifyContent: "center",
  },
  titleContainer: {
    position: "absolute",
    top: 0,
    left: 0,
    zIndex: 1,
    padding: theme.spacing(2),
  },
  mapContainer: {
    width: "100vw",
    height: "100vh",
  },
  resultContainer: {
    position: "absolute",
    padding: theme.spacing(2),
    right: 0,
    zIndex: 1,
    width: "30vw",
  },
}));

const libraries = ["places"];

const mapContainerStyle = {
  width: "100%",
  height: "100%",
};

const options = {
  disableDefaultUI: true,
  zoomControl: true,
  styles: mapStyles,
};

const zoom = 8;

const center = {
  lat: 36.0014,
  lng: -78.9382,
};

const COLORS = [
  "#00ffff",
  "#f0ffff",
  "#f5f5dc",
  "#000000",
  "#0000ff",
  "#a52a2a",
  "#00ffff",
  "#008b8b",
  "#a9a9a9",
  "#006400",
  "#bdb76b",
  "#8b008b",
  "#556b2f",
  "#ff8c00",
  "#9932cc",
  "#8b0000",
  "#e9967a",
  "#9400d3",
  "#ff00ff",
  "#ffd700",
  "#008000",
  "#4b0082",
  "#f0e68c",
  "#add8e6",
  "#e0ffff",
  "#90ee90",
  "#d3d3d3",
  "#ffb6c1",
  "#ffffe0",
  "#00ff00",
  "#ff00ff",
  "#800000",
  "#000080",
  "#808000",
  "#ffa500",
  "#ffc0cb",
  "#800080",
  "#800080",
  "#ff0000",
  "#c0c0c0",
  "#ffffff",
  "#ffff00",
];

export default function Map() {
  const { isLoaded, loadError } = useLoadScript({
    googleMapsApiKey: process.env.REACT_APP_GOOGLE_MAPS_API_KEY,
    libraries,
  });
  const [myCenter, setMyCenter] = React.useState(center);
  const [markers, setMarkers] = React.useState([]);
  const [childrenMarkers, setChildrenMarkers] = React.useState([]);
  const [colors, setColors] = React.useState([]);
  const [selectedLocation, setSelectedLocation] = React.useState(null);
  const mapRef = React.useRef();
  const classes = useStyles();

  const onMapLoad = (map) => {
    mapRef.current = map;
  };

  const panTo = ({ lat, lng }) => {
    mapRef.current.panTo({ lat, lng });
    mapRef.current.setZoom(10);
  };

  const handleSetMarker = (marker) => {
    if (
      markers.find((m) => m.lat === marker.lat && m.lng === marker.lng) !==
      undefined
    )
      return;
    if (markers.length === 2)
      return openSnackbarExternal({
        severity: "error",
        message:
          "You can only select up to two locations - please clear some markers...",
      });
    setMarkers([...markers, marker]);
    panTo(marker);
  };

  const handleSetCurrentLocationMarker = (marker) => {
    if (
      markers.find((m) => m.lat === marker.lat && m.lng === marker.lng) !==
      undefined
    )
      return;
    if (markers.length === 2)
      return openSnackbarExternal({
        severity: "error",
        message:
          "You can only select up to two locations - please clear some markers...",
      });
    handleSetMarker({ lat: marker.lat, lng: marker.lng });
    setMyCenter({ lat: marker.lat, lng: marker.lng });
    panTo(marker);
  };

  const handleDeleteMarker = (marker) => {
    const newMarkers = markers.filter((m) => m !== marker);
    setMarkers(newMarkers);
    setSelectedLocation(null);
  };

  if (loadError) return null;

  if (!isLoaded)
    return (
      <div className={classes.loadingContainer}>
        <CircularProgress />
      </div>
    );

  return (
    <MyMapContext.Provider
      value={{
        markers,
        setMarkers,
        handleSetMarker,
        myCenter,
        handleSetCurrentLocationMarker,
        setSelectedLocation,
        handleDeleteMarker,
        setChildrenMarkers,
      }}
    >
      <div className={classes.root}>
        <div className={classes.mapContainer}>
          <div className={classes.titleContainer}>
            <SearchMap></SearchMap>
          </div>
          <GoogleMap
            mapContainerStyle={mapContainerStyle}
            onLoad={onMapLoad}
            zoom={zoom}
            center={center}
            options={options}
          >
            {markers.map((marker) => (
              <Marker
                key={`${marker.lat}-${marker.lng}`}
                position={{ lat: marker.lat, lng: marker.lng }}
                onMouseOver={() => {
                  if (selectedLocation === marker) return;
                  if (selectedLocation !== null) {
                    setSelectedLocation(null);
                  }
                  setSelectedLocation(marker);
                }}
              />
            ))}
            {childrenMarkers.map((marker) => {
              // console.log(colors);
              // console.log(childrenMarkers);
              return (
                <Marker
                  icon={{
                    path: window.google.maps.SymbolPath.FORWARD_CLOSED_ARROW,
                    strokeColor: colors[marker.clusterNumber].hex(),
                    scale: 4,
                  }}
                  key={`${marker.lat}-${marker.lng}`}
                  position={{ lat: marker.lat, lng: marker.lng }}
                />
              );
            })}
            {selectedLocation ? (
              <InfoBox
                position={{
                  lat: selectedLocation.lat,
                  lng: selectedLocation.lng,
                }}
                options={{
                  closeBoxURL: ``,
                  boxStyle: {
                    width: "400px",
                  },
                  enableEventPropagation: true,
                }}
              >
                <MyInfoBox marker={selectedLocation} />
              </InfoBox>
            ) : null}
          </GoogleMap>
        </div>
        <div className={classes.resultContainer}>
          <ResultBox
            setChildrenMarkers={setChildrenMarkers}
            setColors={setColors}
          />
        </div>
      </div>
    </MyMapContext.Provider>
  );
}
