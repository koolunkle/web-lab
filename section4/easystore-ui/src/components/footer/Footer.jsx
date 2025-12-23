import { faHeart } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { useState } from "react";
import styles from "./footer.module.css";

export default function Footer() {
  const [isActive] = useState(() => Math.random() > 0.5);
  return (
    <>
      <h1
        className={`${styles["my-heading"]} ${
          isActive ? styles["primary-color"] : styles["secondary-color"]
        }`}
      >
        Demo of Global CSS Scope from Home
      </h1>
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
