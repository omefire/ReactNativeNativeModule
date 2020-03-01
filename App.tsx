/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 *
 * Generated with the TypeScript template
 * https://github.com/react-native-community/react-native-template-typescript
 *
 * @format
 */

import React, { useEffect, useState } from 'react';
import {
  SafeAreaView,
  StyleSheet,
  ScrollView,
  View,
  Text,
  StatusBar,
  TextInput,
  Button,
  Alert,
} from 'react-native';

import {
  Header,
  Colors,
} from 'react-native/Libraries/NewAppScreen';

import RNBootSplash from 'react-native-bootsplash';

declare var global: {HermesInternal: null | {}};

const App = () => {
  let init = async () => {
    // ...
  };

  const [textToSign, setTextToSign] = useState("");
  const [digitallySignedText, setDigitallySignedText] = useState("");

  useEffect(() => {
    init().then(() => {
      RNBootSplash.hide({ duration: 1000 });
    });
  }, [])

  return (
    <>
      <StatusBar barStyle="dark-content" />
      <SafeAreaView>
        <ScrollView
          contentInsetAdjustmentBehavior="automatic"
          style={styles.scrollView}>
          <Header />
          {global.HermesInternal == null ? null : (
            <View style={styles.engine}>
              <Text style={styles.footer}>Engine: Hermes</Text>
            </View>
          )}
          <View style={styles.body}>
            <View style={styles.sectionContainer}>
              <Text style={styles.sectionTitle}>Enter text to be signed:</Text>
              <TextInput
                style={{ height: 40, borderColor: 'gray', borderWidth: 1, marginBottom: 15 }}
                value={textToSign}
                onChangeText={(text) => {
                  setTextToSign(text);
                  setDigitallySignedText("");
                }}
               />
               <Button
                title="Digitally sign text"
                color="#f194ff"
                onPress={() => {
                  if(textToSign === "") {
                    Alert.alert("Please, make sure to enter a text to be signed")
                    return
                  }
                  
                  setDigitallySignedText("Omar Mefire");
                }}
                />
            </View>
          </View>
          <View style={styles.body}>
            <View style={styles.sectionContainer}>
              <Text style={styles.sectionTitle}>Digitally signed text:</Text>
              <Text style={{fontSize: 20, fontWeight: '300'}}>{digitallySignedText}</Text>
            </View>
          </View>
        </ScrollView>
      </SafeAreaView>
    </>
  );
};

const styles = StyleSheet.create({
  scrollView: {
    backgroundColor: Colors.lighter,
  },
  engine: {
    position: 'absolute',
    right: 0,
  },
  body: {
    backgroundColor: Colors.white,
  },
  sectionContainer: {
    marginTop: 32,
    paddingHorizontal: 24,
  },
  sectionTitle: {
    fontSize: 24,
    fontWeight: '600',
    color: Colors.black,
  },
  sectionDescription: {
    marginTop: 8,
    fontSize: 18,
    fontWeight: '400',
    color: Colors.dark,
  },
  highlight: {
    fontWeight: '700',
  },
  footer: {
    color: Colors.dark,
    fontSize: 12,
    fontWeight: '600',
    padding: 4,
    paddingRight: 12,
    textAlign: 'right',
  },
});

export default App;
