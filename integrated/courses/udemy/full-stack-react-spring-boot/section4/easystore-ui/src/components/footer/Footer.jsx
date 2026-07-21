import { faHeart } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { useState } from "react";
import styled from "styled-components";
import styles from "./footer.module.css";

const H1 = styled.h1`
  color: #5b21b6;
  text-align: center;
`;

export default function Footer() {
  const [isActive] = useState(() => Math.random() > 0.5);
  return (
    <>
      {/* <H1> Demo of Styled Components from Footer</H1>
      <EazyButton $primary>Submit</EazyButton> */}
      {/* <h1
        className={`${styles["my-heading"]} ${
          isActive ? styles["primary-color"] : styles["secondary-color"]
        }`}
      >
        Demo of Global CSS Scope from Home
      </h1> */}
      <footer className={styles.footer}>
        Built with
        <FontAwesomeIcon
          icon={faHeart}
          className={styles["footer-icon"]}
          aria-hidden="true"
        />
        by
        <a href="https://eazybytes.com/" target="_blank" rel="noreferrer">
          eazybytes
        </a>
      </footer>
    </>
  );
}
